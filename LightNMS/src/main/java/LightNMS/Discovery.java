package LightNMS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import java.util.concurrent.atomic.AtomicReference;

public class Discovery extends AbstractVerticle
{
  @Override
  public void start()
  {
    AtomicReference<JsonObject> json = new AtomicReference<>(new JsonObject());

    try
    {
      vertx.eventBus().consumer("addDiscoveryTable", message ->
      {
        json.set((JsonObject) message.body());

        if(json.get().getString("deviceType").equals("Ping"))
        {
          System.out.println(json.get().getString("deviceName") + " " + json.get().getString("deviceType") + " " + json.get().getString("ip"));
        }

        else
        {
          System.out.println(json.get().getString("deviceName") + " " + json.get().getString("deviceType") + " " + json.get().getString("ip") + " " + json.get().getString("credentialName") + " " + json.get().getString("credentialPassword"));
        }
      });

      vertx.eventBus().send("addDiscoveryTable", json);
    }

    catch (Exception exception)
    {
      System.out.println(exception.getMessage());
    }
  }
}
