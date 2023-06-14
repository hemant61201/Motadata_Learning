package LightNMS;

import com.zaxxer.nuprocess.NuProcess;
import com.zaxxer.nuprocess.NuProcessBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class PollingExecution extends AbstractVerticle
{
  @Override
  public void start(Promise<Void> startPromise)
  {
    vertx.setPeriodic(120_000, timerId ->
    {
      JsonObject message = new JsonObject();

      String param = "Ping";

      message.put("action", "get_MonitorTable");

      message.put("param", param);

      vertx.eventBus().request("database", message.encode(), runResult ->
      {
        if(runResult.succeeded())
        {
          JsonObject getData = (JsonObject) runResult.result().body();

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

          executeCommand("/home/hemant/Music/LightNMS/src/main/resources/BootStrap", requestData.encode(), exeResult ->
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

              JsonObject msg1 = new JsonObject();

              JsonObject msg2 = new JsonObject();

              String parm = finalResult.toString();

              msg1.put("action", "update_MonitorTable");

              msg2.put("action", "add_PollingTable");

              msg1.put("param", parm);

              msg2.put("param", parm);

              vertx.eventBus().send("database", msg1.encode());

              vertx.eventBus().send("database", msg2.encode());

            }
            else
            {
              System.err.println("Process execution failed: " + exeResult.cause().getMessage());

              startPromise.fail(exeResult.cause());
            }
          });
        }
      });
    });

    startPromise.complete();
  }

  private void executeCommand(String command, String input, io.vertx.core.Handler<io.vertx.core.AsyncResult<String>> handler)
  {
    CompletableFuture<String> processOutputFuture = new CompletableFuture<>();

    command += " " + input;

    NuProcessBuilder pb = new NuProcessBuilder(Arrays.asList(command.split("\\s+")));

    pb.setProcessListener(new NuProcessHandler(processOutputFuture));

    vertx.<String>executeBlocking(future ->
    {
      System.out.println(Thread.currentThread().getName());
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