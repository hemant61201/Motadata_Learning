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
import java.util.Map;
//TODO remove unused imports
public class DatabaseOperations extends AbstractVerticle
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private void databaseHandler(Message<Object> message)
  {
    if(message != null)
    {
      try
      {
        JsonObject operationData = new JsonObject(message.body().toString());

        switch (operationData.getString("action"))
        {
          case ConstVariables.ADD:
          {
            Promise<Object> promise = Promise.promise();

            executeCommonQuery(operationData, promise);

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

                LOGGER.error("Add operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;

          case ConstVariables.DELETE:
          {
            Promise<Object> promise = Promise.promise();

            executeCommonQuery(operationData, promise);

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
          break;

          case ConstVariables.UPDATE:
          {
            Promise<Object> promise = Promise.promise();

            executeCommonQuery(operationData, promise);

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

                LOGGER.error("Update operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
        }
      }
      catch (Exception exception)
      {
        LOGGER.error(exception.getMessage());
      }
    }
    else
    {
      message.fail(500, "Invalid action: Message is Null");

      LOGGER.error("Message is null");
    }
  }

  private void executeCommonQuery(JsonObject message, Promise<Object> promise)
  {
    Connection connection = Connectionpool.getConnection();

    if(connection != null)
    {
      if(message != null)
      {
        String query = GenricQuery.getQuery(message);

        if(query != null)
        {
          vertx.<Integer>executeBlocking(blockingPromise ->
          {
            try(PreparedStatement preparedStatement = connection.prepareStatement(query);)
            {
              for (int i = 0; i < message.getJsonArray("paramValues").size(); i++)
              {
                preparedStatement.setObject(i + 1, message.getJsonArray("paramValues").getValue(i));
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

              promise.fail(result.cause());
            }
          });
        }

        else
        {
          LOGGER.error("Query is null in executeCommonQuery");

          promise.fail("");
        }
      }

    }

    else
    {
      LOGGER.error("Connection Failed in executeCommonQuery");

      promise.fail("");
    }
  }

  public static Future<Object> executeBatchQuery(Vertx vertx, JsonObject message)
  {
    Promise<Object> promise = Promise.promise();

    Connection connection = Connectionpool.getConnection();

    if(connection != null)
    {
      String query = GenricQuery.getQuery(message);

      if(query != null)
      {
        vertx.<HashMap<Integer, JsonObject>>executeBlocking(blockingPromise ->
        {
          try (PreparedStatement preparedStatement = connection.prepareStatement(query))
          {
            JsonArray paramValues = message.getJsonArray("paramValues");

            if (paramValues != null)
            {
              for (int i = 0; i < paramValues.size(); i++)
              {
                JsonArray innerArray = paramValues.getJsonArray(i);

                for (int j = 0; j < innerArray.size(); j++)
                {
                  Object value = innerArray.getValue(j);

                  preparedStatement.setObject(j + 1, value);
                }
                preparedStatement.addBatch();
              }
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

            promise.fail(result.cause());
          }
        });
      }

      else
      {
        LOGGER.error("Query is null in ExecuteBatchQuery");

        promise.fail("");
      }

    }
    else
    {
      LOGGER.error("Connection failed in ExecuteBatchQuery");

      promise.fail("");
    }

    return promise.future();
  }

  public static Future<Object> executeGetQuery(Vertx vertx, JsonObject message)
  {
    Promise<Object> promise = Promise.promise();

    Connection connection = Connectionpool.getConnection();

    if(connection != null)
    {
      String query = GenricQuery.getQuery(message);

      if(query != null)
      {
        vertx.<HashMap<Integer, JsonObject>>executeBlocking(blockingPromise ->
        {
          try (PreparedStatement preparedStatement = connection.prepareStatement(query))
          {
            for (int i = 0; i < message.getJsonArray("paramValues").size(); i++)
            {
              preparedStatement.setObject(i + 1, message.getJsonArray("paramValues").getValue(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            if (message.getString("tableName").equals("polling_table"))
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
            else
            {
              HashMap<Integer, JsonObject> resultData = new HashMap<>();

              HashMap<Integer, JsonObject> resultMonitorData = new HashMap<>();

              ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

              int columnCount = resultSetMetaData.getColumnCount();

              int rows = 1;

              JsonObject monitorData = new JsonObject();

              JsonArray ipArray = new JsonArray();

              JsonArray idArray = new JsonArray();

              while (resultSet.next())
              {
                JsonObject rowData = new JsonObject();

                if (message.containsKey("address"))
                {
                  String ip = resultSet.getString("IP");

                  int id = resultSet.getInt("ID");

                  ipArray.add(ip);

                  idArray.add(id);
                }

                else
                {
                  for (int i = 1; i <= columnCount; i++)
                  {
                    String columnName = resultSetMetaData.getColumnName(i);

                    Object columnValue = resultSet.getObject(i);

                    rowData.put(columnName, columnValue);
                  }

                }
                resultData.put(rows, rowData);

                rows++;
              }

              if (message.containsKey("address"))
              {
                monitorData.put("type", message.getJsonArray("paramValues").getValue(0));

                monitorData.put("ip", ipArray);

                monitorData.put("id", idArray);

                resultMonitorData.put(1, monitorData);

                blockingPromise.complete(resultMonitorData);
              }

              else
              {
                LOGGER.info(resultData.toString());

                blockingPromise.complete(resultData);
              }
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

            promise.fail(result.cause());
          }
        });
      }

      else
      {
        LOGGER.error("Query is null in GetQuery");

        promise.fail("");
      }
    }

    else
    {
      LOGGER.error("Connection failed in GetQuery");

      promise.fail("");
    }

    return promise.future();
  }

  public static Future<Object> dashBoardData(Vertx vertx, JsonObject message)
  {
    Promise<Object> promise = Promise.promise();

    Connection connection = Connectionpool.getConnection();

    if(connection != null)
    {
      String query = GenricQuery.getQuery(message);

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

          promise.fail(result.cause());
        }
      });
    }

    else
    {
      LOGGER.error("Connection Not Established");

      promise.fail("");
    }

    return promise.future();
  }

  @Override
  public void start(Promise<Void> startPromise)
  {
    try
    {
      boolean connectionpoolChecker = Connectionpool.createConnection();

      if(connectionpoolChecker)
      {
        JsonObject getPollingData = new JsonObject();

        getPollingData.put("action", "get");

        getPollingData.put("tableName", "");

        getPollingData.put("columns", "m.metric, m.data, m.ip, n.success_count, n.failed_count, n.unknown_count");

        getPollingData.put("condition", "(( SELECT 'Max' AS metric, p.data, p.ip FROM polling_table p WHERE p.metrics = 'Max' AND p.timestamp >= NOW() - INTERVAL '86400' SECOND ORDER BY p.data DESC LIMIT 10) UNION ALL ( SELECT 'Min' AS metric, p.data, p.ip FROM polling_table p WHERE p.metrics = 'Min' AND p.timestamp >= NOW() - INTERVAL '86400' SECOND ORDER BY p.data ASC LIMIT 10)) AS m CROSS JOIN ( SELECT COUNT(CASE WHEN status = 'success' THEN 1 END) AS success_count, COUNT(CASE WHEN status = 'failed' THEN 1 END) AS failed_count, COUNT(CASE WHEN status = 'Unknown' THEN 1 END) AS unknown_count FROM monitor_table) AS n");

        vertx.setPeriodic(120_000, id -> {

          Future<Object> future = dashBoardData(vertx, getPollingData);

          if(future != null)
          {
            future.onComplete(pollingResult ->
            {
              if (pollingResult.succeeded())
              {
                vertx.eventBus().publish("updates.pollingdata", pollingResult.result());
              }

              else
              {
                LOGGER.error("get operation failed on PollingData: " + pollingResult.cause());
              }
            });
          }

          else
          {
            LOGGER.error("Future is null in Polling Data");
          }
        });

        vertx.eventBus().consumer("database",this::databaseHandler);

        startPromise.complete();
      }
      else
      {
        startPromise.fail("Crud operation verticle failed due to not able to create connection pool");
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
    try
    {
      Connectionpool.closeConnections();
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());
    }
  }
}