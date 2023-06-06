package LightNMS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.properties.PropertyFileAuthentication;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Visualization extends AbstractVerticle
{
  @Override
  public void start()
  {
    try
    {
      Router router = Router.router(vertx);

      router.route().handler(BodyHandler.create());

      router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

      PropertyFileAuthentication authenticate = PropertyFileAuthentication.create(vertx,"user.properties");

      router.route("/api/*").handler(RedirectAuthHandler.create(authenticate, "login.html"));

      router.post("/login").handler(FormLoginHandler.create(authenticate));

      router.route("/api/*").handler(StaticHandler.create("api").setCachingEnabled(false));

      router.route().handler(StaticHandler.create());

      router.route("/api/logout").handler(context -> {

        context.clearUser();

        context.redirect("/api/");
      });

      router.post("/addDiscovery").handler(routingContext -> {

        JsonObject json = routingContext.body().asJsonObject();

        vertx.eventBus().send("addDiscoveryTable", json);

      });

      router.get("/monitors").handler(routingContext ->
      {
        JsonObject json = new JsonObject().put("id",1).put("deviceName","hemant").put("ip","10.20.40.156").put("deviceType","Ping").put("status","Unknown");

        routingContext.response().end(json.toString());
      });

      vertx.createHttpServer(new HttpServerOptions()
          .setMaxHeaderSize(32 * 1024)
          .setTcpKeepAlive(true))
        .requestHandler(router)
        .listen(8080);
    }

    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}
