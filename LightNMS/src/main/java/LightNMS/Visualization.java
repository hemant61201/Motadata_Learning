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

        System.out.println(json.getString("ip"));

        vertx.eventBus().request("add_DiscoveryTable", json, result ->
        {
          if (result.succeeded())
          {
            routingContext.response().end(result.result().body().toString());
          }
        });
      });

      router.get("/getDiscoveryTable").handler(routingContext ->
      {
        String message = "get";

        System.out.println(message);

        vertx.eventBus().request("get_DiscoveryTable", message, databaseResult ->
        {
          if(databaseResult.succeeded())
          {
            System.out.println("response" + databaseResult.result().body().toString());
            routingContext.response().end(databaseResult.result().body().toString());
          }
        });
      });

      router.post("/deleteDiscoveryTable").handler(routingContext ->
      {
        String message = routingContext.request().getParam("id");

        System.out.println("message : " + message);

        vertx.eventBus().request("delete_DiscoveryTable", message, deleteResult ->
        {
          if(deleteResult.succeeded())
          {
            routingContext.response().end("success");
          }
        });
      });

      router.post("/updateDiscovery").handler(routingContext ->
      {
        String message = routingContext.request().getParam("id");

        System.out.println("message : " + message);

        vertx.eventBus().request("update_Discovery", message, updateResult ->
        {
          if(updateResult.succeeded())
          {
            System.out.println("update Succesful");

            routingContext.response().end("success");
          }
        });
      });

      router.post("/runDiscovery").handler(routingContext ->
      {
        String message = routingContext.request().getParam("id");

        System.out.println("message :" + message);

        vertx.eventBus().request("run_Discovery", message, runResult ->
        {
          if(runResult.succeeded())
          {
            System.out.println("response  " + runResult.result().body().toString());

            routingContext.response().end("success");
          }
        });
      });

      router.post("/addMonitorTable").handler(routingContext ->
      {
        String message = routingContext.request().getParam("id");

        System.out.println("message :" + message);

        vertx.eventBus().request("get_DiscoveryTable_id", message, rowResult ->
        {
          if (rowResult.succeeded())
          {
            System.out.println("response" + rowResult.result().body().toString());

            vertx.eventBus().request("add_MonitorTable", rowResult.result().body(), runResult ->
            {
              if(runResult.succeeded())
              {
                System.out.println("response  " + runResult.result().body().toString());

                routingContext.response().end("success");
              }
            });
          }
        });
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
