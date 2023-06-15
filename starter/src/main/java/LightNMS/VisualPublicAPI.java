package LightNMS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonArray;
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
import java.util.HashMap;
import java.util.Map;

public class VisualPublicAPI extends AbstractVerticle
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private void handleEventBusRequest(RoutingContext routingContext, String action, Object[] param)
  {
    JsonObject message = new JsonObject();

    message.put("action", action);

    if (param != null)
    {
      message.put("param", param);
    }

    if(action.equals("run_Discovery"))
    {
      vertx.eventBus().request("run_discovery", message.encode(), result ->
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
            routingContext.response().end(result.result().body().toString());
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
    JsonObject json = routingContext.body().asJsonObject();

    if(json != null)
    {
      Object[] param = new Object[7];

      param[0] = json.getString("DEVICENAME");

      param[1] = json.getString("IP");

      param[2] = json.getString("DEVICETYPE");

      param[3] = "Unknown";

      param[4] = json.getJsonObject("CREDENTIAL").toString();

      param[5] = json.getString("IP");

      param[6] = json.getString("DEVICETYPE");

      handleEventBusRequest(routingContext, "add_DiscoveryTable", param);
    }
    else
    {
      LOGGER.info("Null Value for Add Discovery");
    }
  }

  private void handleAddMonitorTable(RoutingContext routingContext)
  {
    String id = routingContext.request().getParam("id");

    if(id != null)
    {
      Object[] param = new Object[]{id};

      Future<Object> future = CrudOperations.executeGetQuery(vertx, "get_DiscoveryTable_id",param);

      if (future != null)
      {
        future.onComplete(asyncResult ->
        {
          if (asyncResult.succeeded())
          {
            @SuppressWarnings("unchecked")
            HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) asyncResult.result();

            JsonObject getData = new JsonObject();

            for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
            {
              getData = entry.getValue();
            }

            Object[] parm = new Object[7];

            parm[0] = getData.getString("DEVICENAME");

            parm[1] = getData.getString("IP");

            parm[2] = getData.getString("DEVICETYPE");

            parm[3] = "Unknown";

            parm[4] = getData.getString("CREDENTIAL");

            parm[5] = getData.getString("IP");

            parm[6] = getData.getString("DEVICETYPE");

            handleEventBusRequest(routingContext, "add_MonitorTable", parm);
          }
          else
          {
            Throwable cause = asyncResult.cause();

            LOGGER.error(cause.getMessage());
          }
        });
      }
      else
      {
        LOGGER.error("Error: Future is null");
      }
    }

    else
    {
      LOGGER.error("Message is null");
    }
  }

  private void handleGetDiscoveryTable(RoutingContext routingContext)
  {
    Object[] param = new Object[0];

    Future<Object> future = CrudOperations.executeGetQuery(vertx, "get_DiscoveryTable",param);

    if (future != null)
    {
      future.onComplete(asyncResult ->
      {
        if (asyncResult.succeeded())
        {
          @SuppressWarnings("unchecked")
          HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) asyncResult.result();

          JsonArray jsonArray = new JsonArray();

          for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
          {
            JsonObject jsonObject = entry.getValue();

            jsonArray.add(jsonObject.encode());
          }

          routingContext.response().end(String.valueOf(jsonArray));
        }
        else
        {
          Throwable cause = asyncResult.cause();

          routingContext.response().setStatusCode(500).end("Error: " + cause.getMessage());
        }
      });
    }
    else
    {
      LOGGER.error("Error: Future is null");

      routingContext.response().setStatusCode(500).end("Error: Future is null");
    }
  }

  private void handleGetMonitorTable(RoutingContext routingContext)
  {
    Object[] param = new Object[0];

    Future<Object> future = CrudOperations.executeGetQuery(vertx, "get_MonitorTable",param);

    if (future != null)
    {
      future.onComplete(asyncResult ->
      {
        if (asyncResult.succeeded())
        {
          @SuppressWarnings("unchecked")
          HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) asyncResult.result();

          JsonArray jsonArray = new JsonArray();

          for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
          {
            JsonObject jsonObject = entry.getValue();

            jsonArray.add(jsonObject.encode());
          }

          routingContext.response().end(String.valueOf(jsonArray));
        }
        else
        {
          Throwable cause = asyncResult.cause();

          routingContext.response().setStatusCode(500).end("Error: " + cause.getMessage());
        }
      });
    }
    else
    {
      LOGGER.error("Error: Future is null");

      routingContext.response().setStatusCode(500).end("Error: Future is null");
    }
  }

  private void handleDeleteDiscoveryTable(RoutingContext routingContext)
  {
    String id = routingContext.request().getParam("id");

    if(id != null)
    {
      Object[] param = new Object[]{id};

      handleEventBusRequest(routingContext, "delete_DiscoveryTable", param);
    }

    else
    {
      LOGGER.error("id is null in DeleteDiscoveryTable");
    }

  }

  private void handleDeleteMonitorTable(RoutingContext routingContext)
  {
    String id = routingContext.request().getParam("id");

    if(id != null)
    {
      Object[] param = new Object[]{id};

      handleEventBusRequest(routingContext, "delete_MonitorTable", param);
    }

    else
    {
      LOGGER.error("id is null in DeleteMonitorTable");
    }


  }

  private void handleUpdateDiscovery(RoutingContext routingContext)
  {
    String[] message = routingContext.request().getParam("id").split("_");

    Object[] param = new Object[3];

    if(message[1].equals("credential"))
    {
      String[] credentialValue = message[2].split("\\.");

      param[0] = "{\"credential_userName\":" + "\"" + credentialValue[0] + "\"" + ",\"credential_password\":" + "\"" +credentialValue[1] + "\"}";
    }

    else
    {
      param[0] = message[2];
    }

    param[1] = "Unknown";

    param[2] = message[0];

    handleEventBusRequest(routingContext, "update_DiscoveryTable_" + message[1], param);
  }

  private void handleRunDiscovery(RoutingContext routingContext)
  {
    String id = routingContext.request().getParam("id");

    if(id != null)
    {
      Object[] param = new Object[]{id};

      handleEventBusRequest(routingContext, "run_Discovery", param);
    }

    else
    {
      LOGGER.error("id is null in RunDiscovery");
    }
  }

  private void handleViewMonitor(RoutingContext routingContext)
  {
    String ip = routingContext.request().getParam("ip");

    if(ip != null)
    {
      Object[] param = new Object[]{ip,ip};

      Future<Object> future = CrudOperations.executeGetQuery(vertx, "get_PollingTable",param);

      if (future != null)
      {
        future.onComplete(asyncResult ->
        {
          if (asyncResult.succeeded())
          {
            @SuppressWarnings("unchecked")
            HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) asyncResult.result();

            JsonObject rowData = new JsonObject();

            for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
            {
              rowData = entry.getValue();
            }

            routingContext.response().end(String.valueOf(rowData));
          }
          else
          {
            Throwable cause = asyncResult.cause();

            routingContext.response().setStatusCode(500).end("Error: " + cause.getMessage());
          }
        });
      }
      else
      {
        routingContext.response().setStatusCode(500).end("Error: Future is null");
      }
    }

    else
    {
      LOGGER.error("ip is null in run discovery");
    }
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

      router.route("/api/logout").handler(context ->
      {
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
        .listen(8080).onComplete(handler -> {
            if(handler.succeeded())
            {
              startPromise.complete();
            }
            else
            {
              vertx.undeploy(VisualPublicAPI.class.getName());
            }
          });
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());

      startPromise.fail(exception);
    }
  }
}
