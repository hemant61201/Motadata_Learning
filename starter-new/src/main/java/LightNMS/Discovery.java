package LightNMS;

import com.zaxxer.nuprocess.NuProcess;
import com.zaxxer.nuprocess.NuProcessBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class Discovery extends AbstractVerticle
{
  private String id;

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  @Override
  public void start(Promise<Void> startPromise)
  {
    try
    {
      vertx.eventBus().consumer("discovery", message ->
      {
        JsonObject requestData = new JsonObject();

        String param = message.body().toString();

        JsonObject msg = new JsonObject();

        msg.put("action", "get_DiscoveryTable_id");

        if (param != null)
        {
          msg.put("param", param);
        }

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

            logger.error("Discovery operation failed: " + cause.getMessage());

            message.fail(500, cause.getMessage());
          }
        });

        vertx.eventBus().request("database", msg.encode(), rowResult ->
        {
          if (rowResult.succeeded())
          {
            JsonObject getData = (JsonObject) rowResult.result().body();

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

            executeCommand("/home/hemant/Music/LightNMS/src/main/resources/BootStrap", requestData.encode(), exeResult ->
            {
              if (exeResult.succeeded())
              {
                String parm = exeResult.result();

                JsonObject msges = new JsonObject();

                msges.put("action", "update_DiscoveryTable");

                if (parm != null)
                {
                  msges.put("param", parm);
                }

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
        });
      });
    }

    catch (Exception exception)
    {
      System.out.println(exception.getMessage());

      startPromise.fail(exception);
    }
  }

  private void executeCommand(String command, String input, io.vertx.core.Handler<io.vertx.core.AsyncResult<String>> handler)
  {
    CompletableFuture<String> processOutputFuture = new CompletableFuture<>();

    command += " " + input;

    NuProcessBuilder pb = new NuProcessBuilder(Arrays.asList(command.split("\\s+")));

    pb.setProcessListener(new NuProcessHandler(processOutputFuture));

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

      future.complete(processOutputFuture.join());

    },false, handler);
  }
}
