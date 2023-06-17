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
    vertx.setPeriodic(20_000, timerId ->
    {
      JsonObject getMonitorData = new JsonObject();

      JsonArray paramValues = new JsonArray();

      paramValues.add("Ping");

      getMonitorData.put("action", "get");

      getMonitorData.put("tableName", "monitor_table");

      getMonitorData.put("columns", "ip,id");

      getMonitorData.put("address", "allIp");

      getMonitorData.put("paramValues", paramValues);

      getMonitorData.put("condition", " WHERE deviceType = ?");

      Future<Object> GetMonitor = DatabaseOperations.executeGetQuery(vertx, getMonitorData);

      if (GetMonitor != null)
      {
        GetMonitor.onComplete(getResult ->
        {
          if (getResult.succeeded())
          {
            @SuppressWarnings("unchecked")
            HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) getResult.result();

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

            String workingDir = System.getProperty("user.dir");

            String bootStrapPath = workingDir + "/src/main/resources/BootStrap";

            executeCommand(bootStrapPath, requestData.encode(), exeResult ->
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

                JsonArray batchParam = new JsonArray();

                for (String id : finalResult.fieldNames())
                {
                  JsonObject metricData = finalResult.getJsonObject(id);

                  JsonArray paramValue = new JsonArray();

                  paramValue.add(metricData.getString("Status"));

                  paramValue.add(Integer.parseInt(metricData.getString("ID")));

                  batchParam.add(paramValue);
                }

                JsonObject updateStatus = new JsonObject();

                updateStatus.put("action","update");

                updateStatus.put("tableName", "monitor_table");

                updateStatus.put("columns", "status");

                updateStatus.put("paramValues", batchParam);

                updateStatus.put("condition", " = ? where id = ?");

                Future<Object> updateMonitor = DatabaseOperations.executeBatchQuery(vertx, updateStatus);

                if (updateMonitor != null)
                {
                  updateMonitor.onComplete(updateResult ->
                  {
                    if (updateResult.succeeded())
                    {
                      LOGGER.info("Execution of Update Monitor Table Successful Completed");
                    }
                    else
                    {
                      LOGGER.error("Error: " + updateResult.cause());
                    }
                  });
                }
                else
                {
                  LOGGER.error("Error: UpdateMonitor is null");
                }

                JsonArray batchParam1 = new JsonArray();

                for (String id : finalResult.fieldNames())
                {
                  JsonObject metricData = finalResult.getJsonObject(id);

                  for (String key : metricData.fieldNames())
                  {
                    if (!key.equals("IP") && !key.equals("ID"))
                    {
                      JsonArray parameter = new JsonArray();

                      parameter.add(key);

                      parameter.add(metricData.getString(key));

                      parameter.add(metricData.getString("IP"));

                      batchParam1.add(parameter);
                    }
                  }
                }

                JsonObject addData = new JsonObject();

                addData.put("action","add");

                addData.put("tableName", "polling_table");

                addData.put("columns", "metrics, data, ip, timestamp");

                addData.put("paramValues", batchParam1);

                addData.put("condition", "VALUES (?, ?, ?, CURRENT_TIMESTAMP)");

                Future<Object> AddPolling = DatabaseOperations.executeBatchQuery(vertx, addData);

                if (AddPolling != null)
                {
                  AddPolling.onComplete(addResult ->
                  {
                    if (addResult.succeeded())
                    {
                      LOGGER.info("Execution of add PollingTable Successful Completed");
                    }
                    else
                    {
                      LOGGER.error("Error: " + addResult.cause());
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
            LOGGER.error("Error: " + getResult.cause());
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
