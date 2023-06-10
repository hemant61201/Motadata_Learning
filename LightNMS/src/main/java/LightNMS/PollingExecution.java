package LightNMS;

import com.zaxxer.nuprocess.NuAbstractProcessHandler;
import com.zaxxer.nuprocess.NuProcess;
import com.zaxxer.nuprocess.NuProcessBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.OutputStream;
import java.nio.ByteBuffer;
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
      JsonObject requestData = new JsonObject()
        .put("Method", "Polling")
        .put("Operation", "Ping")
        .put("credentialProfile", new JsonObject()
          .put("username", "anshu")
          .put("password", "Anushk@2001"))
        .put("discoveryProfile", new JsonObject()
          .put("ip", "10.20.40.156,10.20.40.199,10.20.40.24,10.20.40.64")
          .put("port", 22)
          .put("id", new JsonArray().add(1).add(2).add(3).add(4)));

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
