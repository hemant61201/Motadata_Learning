package LightNMS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import java.util.concurrent.atomic.AtomicReference;

public class Discovery extends AbstractVerticle
{
  @Override
  public void start()
  {
    try
    {
//      vertx.eventBus().consumer("addDiscoveryTable", message ->
//      {
//        vertx.eventBus().send("addDiscoveryTable", message.body());
//      });

    }

    catch (Exception exception)
    {
      System.out.println(exception.getMessage());
    }
  }
}
