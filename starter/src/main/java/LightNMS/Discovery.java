package LightNMS;

import com.zaxxer.nuprocess.NuProcess;
import com.zaxxer.nuprocess.NuProcessBuilder;
import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class Discovery extends AbstractVerticle
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  @Override
  public void start(Promise<Void> startPromise)
  {
    try
    {
      vertx.eventBus().consumer("run_discovery", message ->
      {
        JsonObject requestData = new JsonObject();

        Promise<Object> promise = Promise.promise();

        JsonObject json = new JsonObject(message.body().toString());

        JsonArray paramJsonArray = json.getJsonArray("param");

        Object[] param = new Object[paramJsonArray.size()];

        for (int i = 0; i < paramJsonArray.size(); i++)
        {
          param[i] = paramJsonArray.getValue(i);
        }

        Future<Object> future = CrudOperations.executeGetQuery(vertx, "get_DiscoveryTable_id",param);

        if (future != null)
        {
          future.onComplete(asyncResult ->
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

              String credentialData = getData.getString("CREDENTIAL");

              String[] values = getData.getString("IP").split(",");

              JsonArray ipArray = new JsonArray();

              for (String value : values)
              {
                ipArray.add(value);
              }

              JsonObject credentialObject = new JsonObject(credentialData);

              requestData.put("Method", "Discovery")
                .put("Operation", getData.getString("DEVICETYPE"))
                .put("credentialProfile", new JsonObject()
                  .put("username", credentialObject.getString("credential_userName"))
                  .put("password", credentialObject.getString("credential_password")))
                .put("discoveryProfile", new JsonObject()
                  .put("ip", ipArray)
                  .put("port", 22)
                  .put("id", new JsonArray().add(getData.getInteger("ID"))));

              executeCommand("src/main/resources/BootStrap", requestData.encode(), exeResult ->
              {
                if (exeResult.succeeded())
                {
                  String[] result = exeResult.result().split("_");

                  String number = result[1].trim();

                  String numberString = number.substring(1, number.length() - 1);

                  Object[] parm = new Object[2];

                  parm[0] = result[0];

                  parm[1] = Integer.parseInt(numberString);

                  JsonObject msges = new JsonObject();

                  msges.put("action", "update_DiscoveryTable");

                  msges.put("param", parm);

                  vertx.eventBus().request("database", msges.encode(), updateResult ->
                  {
                    if(updateResult.succeeded())
                    {
                      promise.complete("success");
                    }

                    else
                    {
                      promise.fail(updateResult.cause());
                    }
                  });
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

              LOGGER.error(cause.getMessage());
            }
          });
        }
        else
        {
          LOGGER.error("Error: Future is null");
        }

        promise.future().onComplete(result ->
        {
          if (result.succeeded())
          {
            String reply = result.result().toString();

            message.reply(reply);
          }

          else
          {
            Throwable cause = result.cause();

            LOGGER.error("Discovery operation failed: " + cause.getMessage());

            message.fail(500, cause.getMessage());
          }
        });
      });
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());

      startPromise.fail(exception);
    }
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

        process.waitFor(60, TimeUnit.SECONDS);
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
