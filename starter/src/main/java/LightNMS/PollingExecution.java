package LightNMS;

import com.zaxxer.nuprocess.NuProcess;
import com.zaxxer.nuprocess.NuProcessBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Handler;
import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class PollingExecution extends AbstractVerticle
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  @Override
  public void start(Promise<Void> startPromise)
  {
    vertx.setPeriodic(120_000, timerId ->
    {
      Object[] param = new Object[1];

      param[0] = "Ping";

      Future<Object> GetMonitor = CrudOperations.executeGetQuery(vertx, "get_MonitorTable_data",param);

      if (GetMonitor != null)
      {
        GetMonitor.onComplete(asyncResult ->
        {
          if (asyncResult.succeeded())
          {
            @SuppressWarnings("unchecked")
            HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) asyncResult.result();

            JsonObject getData = new JsonObject();

            for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
            {
              getData = entry.getValue();
            }

            JsonArray ipArray = new JsonArray(getData.getString("ip"));

            JsonArray idArray = new JsonArray(getData.getString("id"));

            JsonObject requestData = new JsonObject()
              .put("Method", "Polling")
              .put("Operation", getData.getString("type"))
              .put("credentialProfile", new JsonObject()
                .put("username", "")
                .put("password", ""))
              .put("discoveryProfile", new JsonObject()
                .put("ip", ipArray)
                .put("port", 22)
                .put("id", idArray));

            executeCommand("src/main/resources/BootStrap", requestData.encode(), exeResult ->
            {
              if (exeResult.succeeded())
              {
                String[] lines = exeResult.result().split("\\r?\\n");

                JsonObject finalResult = new JsonObject();

                for (String line : lines)
                {
                  String[] keyValuePairs = line.replace("{", "")
                    .replace("}", "")
                    .split(" ");

                  JsonObject entryObject = new JsonObject();

                  for (String pair : keyValuePairs)
                  {
                    String[] parts = pair.split(":");

                    String key = parts[0];

                    String value = parts[1];

                    entryObject.put(key, value);
                  }

                  String id = entryObject.getString("ID");

                  finalResult.put(id, entryObject);
                }

                List<Object[]> batchParam = new ArrayList<>();

                for (String id : finalResult.fieldNames())
                {
                  JsonObject metricData = finalResult.getJsonObject(id);

                  Object[] parameter = new Object[2];

                  parameter[0] = metricData.getString("Status");

                  parameter[1] = Integer.parseInt(metricData.getString("ID"));

                  batchParam.add(parameter);
                }

                Future<Object> updateMonitor = CrudOperations.executeBatchQuery(vertx, "update_MonitorTable", batchParam);

                if (updateMonitor != null)
                {
                  updateMonitor.onComplete(result ->
                  {
                    if (result.succeeded())
                    {
                      LOGGER.info("Execution Successful Completed");
                    }
                    else
                    {
                      Throwable cause = result.cause();

                      LOGGER.error("Error: " + cause.getMessage());
                    }
                  });
                }
                else
                {
                  LOGGER.error("Error: UpdateMonitor is null");
                }

                List<Object[]> batchParam1 = new ArrayList<>();

                for (String id : finalResult.fieldNames())
                {
                  JsonObject metricData = finalResult.getJsonObject(id);

                  for (String key : metricData.fieldNames())
                  {
                    if (!key.equals("IP") && !key.equals("ID"))
                    {
                      Object[] parameter = new Object[3];

                      parameter[0] = key;

                      parameter[1] = metricData.getString(key);

                      parameter[2] = metricData.getString("IP");

                      batchParam1.add(parameter);
                    }
                  }
                }

                Future<Object> AddPolling = CrudOperations.executeBatchQuery(vertx, "add_PollingTable",batchParam1);

                if (AddPolling != null)
                {
                  AddPolling.onComplete(result ->
                  {
                    if (result.succeeded())
                    {
                      LOGGER.info("Execution Successful Completed");
                    }
                    else
                    {
                      Throwable cause = result.cause();

                      LOGGER.error("Error: " + cause.getMessage());
                    }
                  });
                }
                else
                {
                  LOGGER.error("Error: AddPolling is null");
                }
              }
              else
              {
                System.err.println("Process execution failed: " + exeResult.cause().getMessage());

                startPromise.fail(exeResult.cause());
              }
            });

          }
          else
          {
            Throwable cause = asyncResult.cause();

            LOGGER.error("Error: " + cause.getMessage());
          }
        });
      }
      else
      {
        LOGGER.error("Error: GetMonitor is null");
      }
    });

    startPromise.complete();
  }

  private void executeCommand(String command, String input, Handler<AsyncResult<String>> handler)
  {
    CompletableFuture<String> processOutputFuture = new CompletableFuture<>();

    command += " " + input;

    NuProcessBuilder pb = new NuProcessBuilder(Arrays.asList(command.split("\\s+")));

    pb.setProcessListener(new NuProcessHandler(processOutputFuture));

    vertx.executeBlocking(future ->
    {
      try
      {
        NuProcess process = pb.start();

        process.waitFor(120, TimeUnit.SECONDS);
      }

      catch (Exception e)
      {
        future.fail(e);

        return;
      }

      future.complete(processOutputFuture.join());

    },false, handler);
  }
}
