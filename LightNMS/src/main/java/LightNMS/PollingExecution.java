package LightNMS;

import com.zaxxer.nuprocess.NuAbstractProcessHandler;
import com.zaxxer.nuprocess.NuProcess;
import com.zaxxer.nuprocess.NuProcessBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class PollingExecution extends AbstractVerticle
{
  @Override
  public void start(Promise<Void> startPromise)
  {
    vertx.setPeriodic(120_000, timerId ->
    {
      JsonObject requestData = new JsonObject()
        .put("Method", "Polling")
        .put("Operation", "Ping")
        .put("credentialProfile", new JsonObject()
          .put("username", "anshu")
          .put("password", "Anushk@2001"))
        .put("discoveryProfile", new JsonObject()
          .put("ip", "10.20.40.64,10.20.30.23,10.20.40.156")
          .put("port", 22)
          .put("id", new JsonArray().add(1).add(2).add(3)));

      executeCommand("/home/hemant/Downloads/starter/src/main/resources/BootStrap", requestData.encode())
        .onComplete(result ->
        {
          if (result.succeeded())
          {
            System.out.println("Process executed successfully");

            System.out.println("Output:\n" + result.result());
          }

          else
          {
            System.err.println("Process execution failed: " + result.cause().getMessage());
          }
        });
    });

    startPromise.complete();
  }

  private io.vertx.core.Future<String> executeCommand(String command, String input)
  {
    Promise<String> processOutputPromise = Promise.promise();

    command += " " + input;

    NuProcessBuilder pb = new NuProcessBuilder(Arrays.asList(command.split("\\s+")));

    pb.setProcessListener(new NuProcessHandler(processOutputPromise));

    vertx.<String>executeBlocking(future ->
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

      future.complete(processOutputPromise.future().result());

    }, false, result ->
    {
      if (result.succeeded())
      {
        if (!processOutputPromise.future().isComplete())
        {
          processOutputPromise.complete(result.result());
        }
      }

      else
      {
        if (!processOutputPromise.future().isComplete())
        {
          processOutputPromise.fail(result.cause());
        }
      }
    });

    return processOutputPromise.future();
  }

  private static class NuProcessHandler extends NuAbstractProcessHandler
  {
    private final io.vertx.core.Promise<String> processOutputPromise;
    private final StringBuilder outputBuffer;

    public NuProcessHandler(io.vertx.core.Promise<String> processOutputPromise)
    {
      this.processOutputPromise = processOutputPromise;

      this.outputBuffer = new StringBuilder();
    }

    @Override
    public void onStdout(ByteBuffer buffer, boolean closed)
    {
      byte[] bytes = new byte[buffer.remaining()];

      buffer.get(bytes);

      outputBuffer.append(new String(bytes));
    }

    @Override
    public void onStderr(ByteBuffer buffer, boolean closed)
    {
      byte[] bytes = new byte[buffer.remaining()];

      buffer.get(bytes);

      outputBuffer.append(new String(bytes));
    }

    @Override
    public void onExit(int statusCode)
    {
      if (!processOutputPromise.future().isComplete())
      {
        processOutputPromise.complete(outputBuffer.toString());
      }
    }
  }
}
