package LightNMS;

import com.zaxxer.nuprocess.NuProcess;
import com.zaxxer.nuprocess.NuProcessBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class Discovery extends AbstractVerticle
{
  private String id;

  @Override
  public void start(Promise<Void> startPromise)
  {
    try
    {
      vertx.eventBus().consumer("run_Discovery", message ->
      {
        JsonObject requestData = new JsonObject();

        id = message.body().toString();

        System.out.println("id : " + id);

        Promise<Object> promise = Promise.promise();

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

            System.out.println("Delete operation failed: " + cause.getMessage());

            message.fail(500, cause.getMessage());
          }
        });

        vertx.eventBus().request("get_DiscoveryTable_id", id, rowResult ->
        {
          if (rowResult.succeeded())
          {
            System.out.println("response" + rowResult.result().body().toString());

            JsonObject getData = (JsonObject) rowResult.result().body();

            String credentialData = getData.getString("CREDENTIAL");

            String[] values = getData.getString("IP").split(",");

            JsonArray ipArray = new JsonArray();

            for (String value : values) {
              ipArray.add(value);
            }

            JsonObject credentialObject = new JsonObject(credentialData);

            System.out.println(credentialObject.getString("credential_userName"));

            requestData.put("Method", "Discovery")
              .put("Operation", getData.getString("DEVICETYPE"))
              .put("credentialProfile", new JsonObject()
              .put("username", credentialObject.getString("credential_userName"))
              .put("password", credentialObject.getString("credential_password")))
              .put("discoveryProfile", new JsonObject()
              .put("ip", ipArray)
              .put("port", 22)
                .put("id", new JsonArray().add(getData.getInteger("ID"))));

            executeCommand("/home/hemant/Music/LightNMS/src/main/resources/BootStrap", requestData.encode(), exeResult ->
            {
              if (exeResult.succeeded())
              {
                System.out.println("Process executed successfully");

                System.out.println("Output:\n" + exeResult.result());

                vertx.eventBus().request("update_DiscoveryTable", exeResult.result(), updateResult ->
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
        });
      });
    }

    catch (Exception exception)
    {
      System.out.println(exception.getMessage());

      startPromise.fail(exception);
    }
//    return null;
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
