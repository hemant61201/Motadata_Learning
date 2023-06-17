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
import java.util.Set;

public class VisualPublicAPI extends AbstractVerticle
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private void eventBusRequestHandler(RoutingContext routingContext, JsonObject message)
  {
    if(message.containsKey("address"))
    {
      vertx.eventBus().request("runDiscovery", message, discoveryResult ->
      {
        if (discoveryResult.succeeded())
        {
          routingContext.response().end(discoveryResult.result().body().toString());
        }
        else
        {
          routingContext.response().setStatusCode(500).end("Error occurred in discoveryResult");
        }
      });
    }

    else
    {
      vertx.eventBus().request("database", message, databaseResult ->
      {
        if (databaseResult.succeeded())
        {
            routingContext.response().end(databaseResult.result().body().toString());
        }
        else
        {
          routingContext.response().setStatusCode(500).end("Error occurred in databaseResult");
        }
      });
    }
  }

  private void addDiscoveryHandler(RoutingContext routingContext)
  {
    JsonObject message = routingContext.body().asJsonObject();

    if(message != null)
    {
      JsonObject addData = new JsonObject();

      JsonArray paramValues = new JsonArray();

      StringBuilder keyString = new StringBuilder();

      Set<String> keys = message.fieldNames();

      for (String key : keys)
      {
        if (key.equals("tableName"))
        {
          continue;
        }

        if (keyString.length() > 0)
        {
          keyString.append(", ");
        }

        keyString.append(key);

        if(key.equals("credential"))
        {
          paramValues.add("{\"credential_userName\":\"\",\"credential_password\":\"\"}");
        }

        else
        {
          paramValues.add(message.getString(key));
        }
      }

      paramValues.add(message.getString("ip"));

      paramValues.add(message.getString("deviceType"));

      String columns = keyString.toString();

      addData.put("action", "add");

      addData.put("tableName", message.getString("tableName"));

      addData.put("columns", columns);

      addData.put("paramValues", paramValues);

      addData.put("condition", "SELECT ?, ?, ?, ?, ? FROM dual WHERE NOT EXISTS ( SELECT 1 FROM " + message.getString("tableName") + " WHERE ip = ? AND deviceType = ?)");

      eventBusRequestHandler(routingContext, addData);
    }
    else
    {
      LOGGER.info("Null Value for Add Discovery");
    }
  }

  private void addMonitorHandler(RoutingContext routingContext)
  {
    JsonObject message = routingContext.body().asJsonObject();

    if(message != null)
    {
      JsonObject getData = new JsonObject();

      JsonArray paramValues = new JsonArray();

      paramValues.add(message.getString("id"));

      getData.put("action", "get");

      getData.put("tableName", message.getString("tableName"));

      getData.put("columns", "*");

      getData.put("paramValues", paramValues);

      getData.put("condition", " where id = ?");

      Future<Object> future = DatabaseOperations.executeGetQuery(vertx, getData);

      if (future != null)
      {
        future.onComplete(asyncResult ->
        {
          if (asyncResult.succeeded())
          {
            @SuppressWarnings("unchecked")
            HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) asyncResult.result();

            JsonObject addData = new JsonObject();

            for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
            {
              addData = entry.getValue();
            }

            JsonArray paramValue = new JsonArray();

            StringBuilder keyString = new StringBuilder();

            Set<String> keys = addData.fieldNames();

            for (String key : keys)
            {
              if (key.equals("tableName") || key.equals("ID"))
              {
                continue;
              }

              if (keyString.length() > 0)
              {
                keyString.append(", ");
              }

              keyString.append(key);

              paramValue.add(addData.getString(key));
            }

            paramValue.add(addData.getString("IP"));

            paramValue.add(addData.getString("DEVICETYPE"));

            String columns = keyString.toString();

            String tableName = "monitor_table";

            addData.put("action", "add");

            addData.put("tableName", tableName);

            addData.put("columns", columns);

            addData.put("paramValues", paramValue);

            addData.put("condition", "SELECT ?, ?, ?, ?, ? FROM dual WHERE NOT EXISTS ( SELECT 1 FROM " + tableName + " WHERE ip = ? AND deviceType = ?)");

            eventBusRequestHandler(routingContext, addData);
          }
          else
          {
            LOGGER.error(asyncResult.cause().getMessage());
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
      LOGGER.error("Message is null in addMonitorHandler");
    }
  }

  private void getHandler(RoutingContext routingContext)
  {
    JsonObject tableName = routingContext.body().asJsonObject();

    if(tableName != null)
    {
      JsonObject getData = new JsonObject();

      JsonArray paramValues = new JsonArray(){};

      getData.put("action", "get");

      getData.put("tableName", tableName.getString("tableName"));

      getData.put("columns", "id,deviceName,ip,deviceType,status");

      getData.put("paramValues", paramValues);

      getData.put("condition", "");

      Future<Object> future = DatabaseOperations.executeGetQuery(vertx, getData);

      if (future != null)
      {
        future.onComplete(getResult ->
        {
          if (getResult.succeeded())
          {
            @SuppressWarnings("unchecked")
            HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) getResult.result();

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
            routingContext.response().setStatusCode(500).end("Error: " + getResult.cause());
          }
        });
      }
      else
      {
        LOGGER.error("Error: Future is null");

        routingContext.response().setStatusCode(500).end("Error: Future is null");
      }
    }

    else
    {
      LOGGER.error("tableName is null");
    }
  }

  private void deleteHandler(RoutingContext routingContext)
  {
    JsonObject message = routingContext.body().asJsonObject();

    if(message != null)
    {
      JsonObject deleteData = new JsonObject();

      JsonArray paramValues = new JsonArray();

      paramValues.add(message.getString("id"));

      deleteData.put("action", "delete");

      deleteData.put("tableName", message.getString("tableName"));

      deleteData.put("columns", "id,deviceName,ip,deviceType,status");

      deleteData.put("paramValues", paramValues);

      deleteData.put("condition", " where id = ?");

      eventBusRequestHandler(routingContext, deleteData);
    }

    else
    {
      LOGGER.error("id is null in DeleteDiscoveryTable");
    }

  }

  private void updateHandler(RoutingContext routingContext)
  {
    JsonObject message = routingContext.body().asJsonObject();

    if(message != null)
    {
      String[] data = message.getString("id").split("_");

      JsonObject updateData = new JsonObject();

      JsonArray paramValues = new JsonArray();

      if(data[1].equals("credential"))
      {
        String[] credentialValue = data[2].split("\\.");

        paramValues.add("{\"credential_userName\":" + "\"" + credentialValue[0] + "\"" + ",\"credential_password\":" + "\"" +credentialValue[1] + "\"}");
      }
      else
      {
        paramValues.add(data[2]);
      }

      paramValues.add("unknown");

      paramValues.add(data[0]);

      updateData.put("action", "update");

      updateData.put("tableName", message.getString("tableName"));

      updateData.put("columns", data[1]);

      updateData.put("paramValues", paramValues);

      updateData.put("condition", " = ?, status = ? where id = ?");

      eventBusRequestHandler(routingContext, updateData);
    }

    else
    {
      LOGGER.error("Message is Null in updateHandler");
    }

  }

  private void runDiscoveryHandler(RoutingContext routingContext)
  {
    JsonObject message = routingContext.body().asJsonObject();

    if(message != null)
    {
      message.put("address", "runDiscovery");

      eventBusRequestHandler(routingContext, message);
    }

    else
    {
      LOGGER.error("id is null in runDiscoveryHandler");
    }
  }

  private void viewMonitorHandler(RoutingContext routingContext)
  {
    JsonObject message = routingContext.body().asJsonObject();

    if(message != null)
    {
      JsonObject getMonitor = new JsonObject();

      JsonArray paramValues = new JsonArray();

      paramValues.add(message.getString("ip"));

      paramValues.add(message.getString("ip"));

      getMonitor.put("action", "get");

      getMonitor.put("tableName", message.getString("tableName"));

      getMonitor.put("columns", "p.metrics, p.data, p.ip, p.timestamp");

      getMonitor.put("paramValues", paramValues);

      getMonitor.put("condition", " p WHERE (metrics IN ('Loss', 'Status') AND timestamp = (SELECT MAX(timestamp) FROM " + message.getString("tableName") + " WHERE metrics = p.metrics)) OR (metrics IN ('Min', 'Max', 'Avg') AND timestamp >= NOW() - INTERVAL '86400' SECOND AND ip = ?) OR (metrics = 'Status' AND timestamp >= NOW() - INTERVAL '86400' SECOND AND ip = ?) ORDER BY p.metrics");

      Future<Object> future = DatabaseOperations.executeGetQuery(vertx, getMonitor);

      if (future != null)
      {
        future.onComplete(viewResult ->
        {
          if (viewResult.succeeded())
          {
            @SuppressWarnings("unchecked")
            HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) viewResult.result();

            JsonObject rowData = new JsonObject();

            for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
            {
              rowData = entry.getValue();
            }

            routingContext.response().end(String.valueOf(rowData));
          }
          else
          {
            routingContext.response().setStatusCode(500).end("Error: " + viewResult.cause());
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
      LOGGER.error("Message is null in run discovery");
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

      router.post("/addDiscovery").handler(this::addDiscoveryHandler);

      router.post("/addMonitorTable").handler(this::addMonitorHandler);

      router.post("/getDiscovery").handler(this::getHandler);

      router.post("/getMonitorTable").handler(this::getHandler);

      router.post("/deleteDiscoveryTable").handler(this::deleteHandler);

      router.post("/deleteMonitorTable").handler(this::deleteHandler);

      router.post("/updateDiscovery").handler(this::updateHandler);

      router.post("/runDiscovery").handler(this::runDiscoveryHandler);

      router.post("/viewMonitor").handler(this::viewMonitorHandler);

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
              startPromise.fail("VisualPublicAPi failed due to HttpServer Problem");
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
