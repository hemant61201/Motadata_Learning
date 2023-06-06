package LightNMS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class QueryService extends AbstractVerticle
{
  @Override
  public void start(Promise<Void> startPromise)
  {
    try
    {
      vertx.eventBus().consumer("addDiscoveryTable", message ->
      {
        JsonObject jsonObject = (JsonObject) message.body();
      });
    }

    catch (Exception exception)
    {
      System.out.println(exception.getMessage());
    }
  }
}
