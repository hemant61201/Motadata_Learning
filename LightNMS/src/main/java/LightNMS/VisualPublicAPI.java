package LightNMS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.properties.PropertyFileAuthentication;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class VisualPublicAPI extends AbstractVerticle
{
  @Override
  public void start(Promise<Void> startPromise)
  {
    try
    {
      Router router = Router.router(vertx);

      router.route().handler(BodyHandler.create());

      router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

      PropertyFileAuthentication authenticate = PropertyFileAuthentication.create(vertx,"user.properties");

      router.route("/api/*").handler(RedirectAuthHandler.create(authenticate, "/login.html"));

      router.post("/login").handler(FormLoginHandler.create(authenticate));
      // This is for Login
      router.route().handler(StaticHandler.create());
      // This is for index.html
      router.route("/api/*").handler(StaticHandler.create("api").setCachingEnabled(false));

      router.route("/api/logout").handler(context -> {
        // to check the clearUSer Usecase
        context.clearUser();

        context.redirect("/api/login.html");
      });

      router.post("/addDiscovery").handler(routingContext -> {

        JsonObject json = routingContext.body().asJsonObject();

        vertx.eventBus().request("add_DiscoveryTable", json, result ->
        {
          if (result.succeeded())
          {
            routingContext.response().end(result.result().body().toString());
          }
        });
      });

      router.post("/addMonitorTable").handler(routingContext ->
      {
        String message = routingContext.request().getParam("id");

        vertx.eventBus().request("get_DiscoveryTable_id", message, rowResult ->
        {
          if (rowResult.succeeded())
          {
            vertx.eventBus().request("add_MonitorTable", rowResult.result().body(), runResult ->
            {
              if(runResult.succeeded())
              {
                routingContext.response().end("success");
              }
            });
          }
        });
      });

      router.get("/getDiscoveryTable").handler(routingContext ->
      {
        String message = "get";

        vertx.eventBus().request("get_DiscoveryTable", message, databaseResult ->
        {
          if(databaseResult.succeeded())
          {
            routingContext.response().end(databaseResult.result().body().toString());
          }
        });
      });

      router.get("/getMonitorTable").handler(routingContext ->
      {
        String message = "monitorGet";

        vertx.eventBus().request("get_MonitorTable_data", message, databaseResult ->
        {
          if(databaseResult.succeeded())
          {
            routingContext.response().end(databaseResult.result().body().toString());
          }
        });
      });

      router.post("/deleteDiscoveryTable").handler(routingContext ->
      {
        String message = routingContext.request().getParam("id");

        vertx.eventBus().request("delete_DiscoveryTable", message, deleteResult ->
        {
          if(deleteResult.succeeded())
          {
            routingContext.response().end("success");
          }
        });
      });

      router.post("/deleteMonitorTable").handler(routingContext ->
      {
        String message = routingContext.request().getParam("id");

        vertx.eventBus().request("delete_MonitorTable", message, deleteResult ->
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

        vertx.eventBus().request("update_Discovery", message, updateResult ->
        {
          if(updateResult.succeeded())
          {
            routingContext.response().end("success");
          }
        });
      });

      router.post("/runDiscovery").handler(routingContext ->
      {
        String message = routingContext.request().getParam("id");

        vertx.eventBus().request("run_Discovery", message, runResult ->
        {
          if(runResult.succeeded())
          {
            routingContext.response().end("success");
          }
        });
      });

      router.post("/viewMonitor").handler(routingContext ->
      {
        String message = routingContext.request().getParam("ip");

        vertx.eventBus().request("get_PollingTable", message, viewResult ->
        {
          if(viewResult.succeeded())
          {
            routingContext.response().end(viewResult.result().body().toString());
          }
        });
      });

      SockJSHandler jsHandler = SockJSHandler.create(vertx);

      SockJSBridgeOptions bridgeOptions = new SockJSBridgeOptions()
        .addInboundPermitted(new PermittedOptions().setAddressRegex("updates.*"))
        .addOutboundPermitted(new PermittedOptions().setAddressRegex("updates.*"));

      router.mountSubRouter("/api/eventbus",jsHandler.bridge(bridgeOptions));
      // is TCP keep alive required then why?
      vertx.createHttpServer(new HttpServerOptions()
          .setTcpKeepAlive(true))
        .requestHandler(router)
        .listen(8080);

      // write a handler to check whether deployment is complete or not
      startPromise.complete();
    }

    catch (Exception exception)
    {
      System.out.println(exception.getMessage());

      startPromise.fail(exception);
    }
  }
}
