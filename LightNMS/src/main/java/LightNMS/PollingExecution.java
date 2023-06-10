package LightNMS;

import com.zaxxer.nuprocess.NuProcess;
import com.zaxxer.nuprocess.NuProcessBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.StringReader;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class PollingExecution extends AbstractVerticle
{
  @Override
  public void start(Promise<Void> startPromise)
  {
    vertx.setPeriodic(20000, timerId ->
    {
      String deviceType = "Ping";

      vertx.eventBus().request("get_MonitorTable", deviceType, runResult ->
      {
        if(runResult.succeeded())
        {
          System.out.println("response  " + runResult.result().body().toString());

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

          System.out.println(requestData);

          executeCommand("/home/hemant/Music/LightNMS/src/main/resources/BootStrap", requestData.encode(), exeResult ->
          {
            if (exeResult.succeeded())
            {
              System.out.println("Process executed successfully");

              System.out.println("Output:\n" + exeResult.result());
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
