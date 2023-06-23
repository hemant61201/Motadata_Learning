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
      vertx.eventBus().consumer(ConstVariables.DISCOVERY, message ->
      {
        JsonObject requestData = new JsonObject();

        Promise<Object> promise = Promise.promise();

        JsonObject discoveryData = new JsonObject(message.body().toString());

        JsonObject getDiscoveryData = new JsonObject();

        JsonArray paramValues = new JsonArray();

        paramValues.add(discoveryData.getString("id"));

        getDiscoveryData.put(ConstVariables.ACTION, "get");

        getDiscoveryData.put(ConstVariables.TABLENAME, discoveryData.getString("tableName"));

        getDiscoveryData.put(ConstVariables.COLUMNS, "*");

        getDiscoveryData.put(ConstVariables.PARAMVALUES, paramValues);

        getDiscoveryData.put(ConstVariables.CONDITION, " where id = ?");

        Future<Object> future = DatabaseOperations.executeGetQuery(vertx, getDiscoveryData);

        if (future != null)
        {
          future.onComplete(getResult ->
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

              String credentialData = getData.getString("CREDENTIAL");

              String[] values = getData.getString("IP").split(",");

              JsonArray ipArray = new JsonArray();

              for (String value : values)
              {
                ipArray.add(value);
              }

              JsonObject credentialObject = new JsonObject(credentialData);

              JsonArray userName = new JsonArray();

              userName.add(credentialObject.getString("credential_userName"));

              JsonArray password = new JsonArray();

              password.add(credentialObject.getString("credential_password"));

              requestData.put("Method", "Discovery")
                .put("Operation", getData.getString("DEVICETYPE"))
                .put("credentialProfile", new JsonObject()
                  .put("username", userName)
                  .put("password", password))
                .put("discoveryProfile", new JsonObject()
                  .put("ip", ipArray)
                  .put("port", 22)
                  .put("id", new JsonArray().add(getData.getInteger("ID"))));

              executeCommand(ConstVariables.BOOTSTRAPPATH, requestData.encode(), exeResult ->
              {
                if (exeResult.succeeded())
                {
                  String[] result = exeResult.result().split("_");

                  String id = result[1].trim();

                  String idString = id.substring(1, id.length() - 1);

                  JsonObject updateStatus = new JsonObject();

                  JsonArray paramValue = new JsonArray();

                  paramValue.add(result[0]);

                  paramValue.add(idString);

                  updateStatus.put(ConstVariables.ACTION,"update");

                  updateStatus.put(ConstVariables.TABLENAME, discoveryData.getString("tableName"));

                  updateStatus.put(ConstVariables.COLUMNS, "status");

                  updateStatus.put(ConstVariables.PARAMVALUES, paramValue);

                  updateStatus.put(ConstVariables.CONDITION, " = ? where id = ?");

                  vertx.eventBus().request("database", updateStatus, updateResult ->
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
                  LOGGER.info("Process execution failed: " + exeResult.cause().getMessage());

                  startPromise.fail(exeResult.cause());
                }
              });
            }
            else
            {
              LOGGER.error(getResult.cause().getMessage());
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
            LOGGER.error("Discovery operation failed: " + result.cause());

            message.fail(500, result.cause().getMessage());
          }
        });
      });

      startPromise.complete();
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());

      startPromise.fail(exception);
    }
  }

  private void executeCommand(String command, String input, Handler<AsyncResult<String>> handler)
  {
    try
    {
      CompletableFuture<String> processOutputFuture = new CompletableFuture<>();

      command += " " + input;

      NuProcessBuilder nuProcessBuilder = new NuProcessBuilder(Arrays.asList(command.split("\\s+")));

      nuProcessBuilder.setProcessListener(new NuProcessHandler(processOutputFuture));

      vertx.executeBlocking(future ->
      {
        try
        {
          NuProcess process = nuProcessBuilder.start();

          process.waitFor(120, TimeUnit.SECONDS);
        }

        catch (Exception exception)
        {
          future.fail(exception);

          return;
        }

        String output = processOutputFuture.join();

        String[] outputLines = output.split("\\r?\\n");

        StringBuilder successOutput = new StringBuilder();

        for (String line : outputLines)
        {
          // Filter out error lines based on a specific pattern
          if (!line.contains("Error"))
          {
            successOutput.append(line).append(System.lineSeparator());
          }
          else
          {
            LOGGER.error(line);
          }
        }

        future.complete(successOutput.toString());

      },false, handler);
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());
    }
  }
}
