package LightNMS;

import LightNMS.Database.Connectionpool;
import LightNMS.Database.GenricQuery;
import LightNMS.Database.MetricsData;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//TODO remove unused imports
public class CrudOperations extends AbstractVerticle
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private void handleCRUDOperation(Message<Object> message)
  {
    {
      String updateAddress = null;
      // null check
      JsonObject json = new JsonObject(message.body().toString());

      // null check
      String action = json.getString("action");

      JsonArray paramJsonArray = json.getJsonArray("param");

      if(paramJsonArray != null && action != null)
      {
        Object[] param = new Object[paramJsonArray.size()];

        for (int i = 0; i < paramJsonArray.size(); i++)
        {
          param[i] = paramJsonArray.getValue(i);
        }

        if (action.contains("update_DiscoveryTable_"))
        {
          updateAddress = action;

          action = "update_Discovery";
        }

        switch (action)
        {
          case "add_DiscoveryTable":
          {
            Promise<Object> promise = Promise.promise();

            executeCommonQuery(action, param, promise);

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

                LOGGER.error("Add DiscoveryTable operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }

          case "add_MonitorTable":
          {
            Promise<Object> promise = Promise.promise();

            executeCommonQuery(action, param, promise);

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

                LOGGER.error("Add MonitorTable operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }

          case "delete_DiscoveryTable":
          {
            Promise<Object> promise = Promise.promise();

            executeCommonQuery(action, param, promise);

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

                LOGGER.error("Delete operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }

          case "delete_MonitorTable":
          {
            Promise<Object> promise = Promise.promise();

            executeCommonQuery(action, param, promise);

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

                LOGGER.error("Delete Monitor operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }

          case "update_Discovery":
          {
            Promise<Object> promise = Promise.promise();

            executeCommonQuery(updateAddress, param, promise);

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

                LOGGER.error("UpdateDiscovery operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }

          case "update_DiscoveryTable":
          {
            Promise<Object> promise = Promise.promise();

            executeCommonQuery(action, param, promise);

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

                LOGGER.error("UpdateDiscovery Status operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }

          default:
            LOGGER.info("Invalid action: " + action);

            message.fail(500, "Invalid action: " + action);
        }
      }

      else
      {
        LOGGER.error("Message is null");
      }
    }
  }

  private void executeCommonQuery(String address, Object[] parameters, Promise<Object> promise)
  {
    Connection connection = Connectionpool.getConnection();

    if(connection != null)
    {
      String query = GenricQuery.getQuery(address);

      if(query != null)
      {
        vertx.<Integer>executeBlocking(blockingPromise ->
        {
          try(PreparedStatement preparedStatement = connection.prepareStatement(query);)
          {
            for (int i = 0; i < parameters.length; i++)
            {
              preparedStatement.setObject(i + 1, parameters[i]);
            }

            int updatedRow;

            updatedRow = preparedStatement.executeUpdate();

            blockingPromise.complete(updatedRow);

          }
          catch (Exception exception)
          {
            LOGGER.error(exception.getMessage());

            blockingPromise.fail(exception);
          }
        }, false).onComplete(result ->
        {
          if (result.succeeded())
          {
            Connectionpool.removeConnection(connection);

            Integer updatedRow = result.result();

            promise.complete(updatedRow != null ? updatedRow.toString() : null);
          }
          else
          {
            Connectionpool.removeConnection(connection);

            Throwable cause = result.cause();

            promise.fail(cause);
          }
        });
      }

      else
      {
        LOGGER.error("Query is null in executeCommonQuery");
      }
    }

    else
    {
      LOGGER.error("Connection Failed in executeCommonQuery");
    }
  }

  public static Future<Object> executeBatchQuery(Vertx vertx, String address, List<Object[]> batchParams)
  {
    Promise<Object> promise = Promise.promise();

    Connection connection = Connectionpool.getConnection();

    if(connection != null)
    {
      String query = GenricQuery.getQuery(address);

      if(query != null)
      {
        vertx.<HashMap<Integer, JsonObject>>executeBlocking(blockingPromise ->
        {
          try (PreparedStatement preparedStatement = connection.prepareStatement(query))
          {
            for (Object[] params : batchParams)
            {
              for (int i = 0; i < params.length; i++)
              {
                preparedStatement.setObject(i + 1, params[i]);
              }

              preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

            blockingPromise.complete();
          }

          catch (Exception exception)
          {
            LOGGER.error(exception.getMessage());

            blockingPromise.fail(exception);
          }
        }, false).onComplete(result ->
        {
          if (result.succeeded())
          {
            Connectionpool.removeConnection(connection);

            promise.complete("Success");
          }
          else
          {
            Connectionpool.removeConnection(connection);

            Throwable cause = result.cause();

            promise.fail(cause);
          }
        });
      }

      else
      {
        LOGGER.error("Query is null in ExecuteBatchQuery");
      }

    }
    else
    {
      LOGGER.error("Connection failed in ExecuteBatchQuery");
    }

    return promise.future();
  }

  public static Future<Object> executeGetQuery(Vertx vertx, String address,Object[] params)
  {
    Promise<Object> promise = Promise.promise();

    Connection connection = Connectionpool.getConnection();

    if(connection != null)
    {
      String query = GenricQuery.getQuery(address);

      if(query != null)
      {
        vertx.<HashMap<Integer, JsonObject>>executeBlocking(blockingPromise ->
        {
          try (PreparedStatement preparedStatement = connection.prepareStatement(query))
          {
            for (int i = 0; i < params.length; i++)
            {
              preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            if (address.equals("get_PollingTable"))
            {
              HashMap<Integer, JsonObject> pollingData = new HashMap<>();

              ObjectMapper objectMapper = new ObjectMapper();

              ObjectNode jsonNode = objectMapper.createObjectNode();

              int successCount = 0;

              int failedCount = 0;

              while (resultSet.next())
              {
                String metrics = resultSet.getString("metrics");

                String data = resultSet.getString("data");

                if (metrics.equals("Max") || metrics.equals("Min") || metrics.equals("Avg"))
                {
                  jsonNode.withArray(metrics).add(data);
                }
                else if (metrics.equals("Loss"))
                {
                  jsonNode.put(metrics, data);
                }
                else if (metrics.equals("Status"))
                {
                  if (data.equals("success"))
                  {
                    successCount++;
                  }
                  else if (data.equals("failed"))
                  {
                    failedCount++;
                  }
                }
              }

              ObjectNode statusNode = objectMapper.createObjectNode();

              statusNode.put("success", successCount);

              statusNode.put("failed", failedCount);

              jsonNode.set("Status", statusNode);

              String jsonString = objectMapper.writeValueAsString(jsonNode);

              JsonObject viewObject = new JsonObject(jsonString);

              pollingData.put(1, viewObject);

              blockingPromise.complete(pollingData);
            }
            else if (address.equals("get_MonitorTable_data"))
            {
              HashMap<Integer, JsonObject> resultData = new HashMap<>();

              JsonObject monitorData = new JsonObject();

              monitorData.put("type", params[0]);

              JsonArray ipArray = new JsonArray();

              JsonArray idArray = new JsonArray();

              while (resultSet.next())
              {
                String ip = resultSet.getString("IP");

                int id = resultSet.getInt("ID");

                ipArray.add(ip);

                idArray.add(id);
              }

              monitorData.put("ip", ipArray);

              monitorData.put("id", idArray);

              resultData.put(1, monitorData);

              blockingPromise.complete(resultData);
            }
            else
            {
              ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

              int columnCount = resultSetMetaData.getColumnCount();

              HashMap<Integer, JsonObject> resultData = new HashMap<>();

              int rows = 1;

              while (resultSet.next())
              {
                JsonObject rowData = new JsonObject();

                for (int i = 1; i <= columnCount; i++)
                {
                  String columnName = resultSetMetaData.getColumnName(i);

                  Object columnValue = resultSet.getObject(i);

                  rowData.put(columnName, columnValue);
                }
                resultData.put(rows, rowData);

                rows++;
              }

              LOGGER.info(resultData.toString());

              blockingPromise.complete(resultData);
            }
          }

          catch (Exception exception)
          {
            LOGGER.error(exception.getMessage());

            blockingPromise.fail(exception);
          }
        }, false).onComplete(result ->
        {
          if (result.succeeded())
          {
            Connectionpool.removeConnection(connection);

            HashMap<Integer, JsonObject> resultData = result.result();

            promise.complete(resultData);
          }
          else
          {
            Connectionpool.removeConnection(connection);

            Throwable cause = result.cause();

            promise.fail(cause);
          }
        });
      }

      else
      {
        LOGGER.error("Query is null in GetQuery");
      }
    }

    else
    {
      LOGGER.error("Connection failed in GetQuery");
    }

    return promise.future();
  }

  private void dashBoardData(Promise<JsonObject> promise)
  {
    Connection connection = Connectionpool.getConnection();

    if(connection != null)
    {
      String query = GenricQuery.livePollingData();

      vertx.executeBlocking(blockingPromise ->
      {
        JsonObject pollingData;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
          ResultSet queryResult = preparedStatement.executeQuery();

          ObjectMapper mapper = new ObjectMapper();

          MetricsData metricsData = new MetricsData();

          metricsData.setMax(new HashMap<>());

          metricsData.setMin(new HashMap<>());

          while (queryResult.next())
          {
            String metric = queryResult.getString("metric");

            String ip = queryResult.getString("ip");

            String value = queryResult.getString("data");

            if (metric.equals("Max"))
            {
              Map<String, String> maxMap = metricsData.getMax().getOrDefault(metric, new HashMap<>());

              maxMap.put(ip, value);

              metricsData.getMax().put(metric, maxMap);
            }

            else if (metric.equals("Min"))
            {
              Map<String, String> minMap = metricsData.getMin().getOrDefault(metric, new HashMap<>());

              minMap.put(ip, value);

              metricsData.getMin().put(metric, minMap);
            }
          }

          if (queryResult.last())
          {
            metricsData.setSuccess(Integer.toString(queryResult.getInt("success_count")));

            metricsData.setFailed(Integer.toString(queryResult.getInt("failed_count")));

            metricsData.setUnknown(Integer.toString(queryResult.getInt("unknown_count")));
          }

          String jsonData = mapper.writeValueAsString(metricsData);

          pollingData = new JsonObject(jsonData);

          blockingPromise.complete(pollingData);
        }

        catch (Exception exception)
        {
          LOGGER.error(exception.getMessage());
        }

      }, false).onComplete(result ->
      {
        if(result.succeeded())
        {
          Connectionpool.removeConnection(connection);

          promise.complete((JsonObject) result.result());
        }
        else
        {
          Connectionpool.removeConnection(connection);

          Throwable cause = result.cause();

          promise.fail(cause);
        }
      });
    }

    else
    {
      LOGGER.error("Connection Not Established");
    }

  }

  @Override
  public void start(Promise<Void> startPromise)
  {
    try
    {
      boolean connectionpoolChecker = Connectionpool.createConnection();

      if(connectionpoolChecker)
      {
        vertx.setPeriodic(5000, id -> {

          Promise<JsonObject> promise = Promise.promise();

          dashBoardData(promise);

          promise.future().onComplete(result ->
          {
            if (result.succeeded())
            {
              vertx.eventBus().publish("updates.pollingdata",result.result());
            }

            else
            {
              Throwable cause = result.cause();

              System.out.println("Add operation failed: " + cause.getMessage());
            }
          });
        });

        vertx.eventBus().consumer("database",this::handleCRUDOperation);

        startPromise.complete();
      }
      else
      {
        vertx.undeploy(CrudOperations.class.getName());
      }
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());

      startPromise.fail(exception);
    }
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception
  {
    Connectionpool.closeConnections();
  }
}
