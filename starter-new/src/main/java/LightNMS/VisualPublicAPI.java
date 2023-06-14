package LightNMS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.properties.PropertyFileAuthentication;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisualPublicAPI extends AbstractVerticle
{
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  private void handleEventBusRequest(RoutingContext routingContext, String action, String param)
  {
    JsonObject message = new JsonObject();

    message.put("action", action);

    if (param != null)
    {
      message.put("param", param);
    }

    if(action.equals("run_Discovery"))
    {
      vertx.eventBus().request("discovery", param, result ->
      {
        if (result.succeeded())
        {
          routingContext.response().end(result.result().body().toString());
        }
        else
        {
          routingContext.response().setStatusCode(500).end("Error occurred");
        }
      });
    }

    else
    {
      vertx.eventBus().request("database", message.encode(), result ->
      {
        if (result.succeeded())
        {
          if(action.equals("get_DiscoveryTable_id"))
          {
            handleEventBusRequest(routingContext, "add_MonitorTable", result.result().body().toString());
          }
          else
          {
            routingContext.response().end(result.result().body().toString());
          }
        }
        else
        {
          routingContext.response().setStatusCode(500).end("Error occurred");
        }
      });
    }
  }

  private void handleAddDiscovery(RoutingContext routingContext)
  {
    JsonObject json = routingContext.getBodyAsJson();

    handleEventBusRequest(routingContext, "add_DiscoveryTable", json.toString());
  }

  private void handleAddMonitorTable(RoutingContext routingContext)
  {
    String id = routingContext.request().getParam("id");

    handleEventBusRequest(routingContext, "get_DiscoveryTable_id", id);
  }

  private void handleGetDiscoveryTable(RoutingContext routingContext)
  {
    handleEventBusRequest(routingContext, "get_DiscoveryTable", null);
  }

  private void handleGetMonitorTable(RoutingContext routingContext)
  {
    handleEventBusRequest(routingContext, "get_MonitorTable_data", null);
  }

  private void handleDeleteDiscoveryTable(RoutingContext routingContext)
  {
    String id = routingContext.request().getParam("id");

    handleEventBusRequest(routingContext, "delete_DiscoveryTable", id);
  }

  private void handleDeleteMonitorTable(RoutingContext routingContext)
  {
    String id = routingContext.request().getParam("id");

    handleEventBusRequest(routingContext, "delete_MonitorTable", id);
  }

  private void handleUpdateDiscovery(RoutingContext routingContext)
  {
    String id = routingContext.request().getParam("id");

    handleEventBusRequest(routingContext, "update_Discovery", id);
  }

  private void handleRunDiscovery(RoutingContext routingContext)
  {
    String id = routingContext.request().getParam("id");

    handleEventBusRequest(routingContext, "run_Discovery", id);
  }

  private void handleViewMonitor(RoutingContext routingContext)
  {
    String ip = routingContext.request().getParam("ip");

    handleEventBusRequest(routingContext, "get_PollingTable", ip);
  }


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

      router.post("/addDiscovery").handler(this::handleAddDiscovery);

      router.post("/addMonitorTable").handler(this::handleAddMonitorTable);

      router.get("/getDiscoveryTable").handler(this::handleGetDiscoveryTable);

      router.get("/getMonitorTable").handler(this::handleGetMonitorTable);

      router.post("/deleteDiscoveryTable").handler(this::handleDeleteDiscoveryTable);

      router.post("/deleteMonitorTable").handler(this::handleDeleteMonitorTable);

      router.post("/updateDiscovery").handler(this::handleUpdateDiscovery);

      router.post("/runDiscovery").handler(this::handleRunDiscovery);

      router.post("/viewMonitor").handler(this::handleViewMonitor);

      SockJSHandler jsHandler = SockJSHandler.create(vertx);

      SockJSBridgeOptions bridgeOptions = new SockJSBridgeOptions()
        .addInboundPermitted(new PermittedOptions().setAddressRegex("updates.*"))
        .addOutboundPermitted(new PermittedOptions().setAddressRegex("updates.*"));

      router.route("/api/eventbus/*").subRouter(jsHandler.bridge(bridgeOptions));

      vertx.createHttpServer(new HttpServerOptions()
          .setTcpKeepAlive(true))
        .requestHandler(router)
        .listen(8080);

      startPromise.complete();
    }

    catch (Exception exception)
    {
      logger.error(exception.getMessage());

      startPromise.fail(exception);
    }
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception
  {
    vertx.undeploy(VisualPublicAPI.class.getName());

    stopPromise.complete();
  }
}
