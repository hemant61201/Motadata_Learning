package LightNMS;

import LightNMS.Database.Connectionpool;
import LightNMS.Database.GenricQuery;
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

public class DatabaseOperations extends AbstractVerticle
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private static Connectionpool connectionPool = null;

  private static boolean connectionpoolChecker = false;

  static
  {
    connectionPool = Connectionpool.getInstance();

    connectionpoolChecker = connectionPool.createConnection();
  }

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
    Connection connection = connectionPool.getConnection();

    if(connection != null)
    {
      if(message != null)
      {
        String query = GenricQuery.getQuery(message);

        if(query != null)
        {
          vertx.<Integer>executeBlocking(blockingPromise ->
          {
            try(PreparedStatement preparedStatement = connection.prepareStatement(query))
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
              connectionPool.removeConnection(connection);

              Integer updatedRow = result.result();

              promise.complete(updatedRow != null ? updatedRow.toString() : null);
            }
            else
            {
              connectionPool.removeConnection(connection);

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

    Connection connection = connectionPool.getConnection();

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
            connectionPool.removeConnection(connection);

            promise.complete("Success");
          }
          else
          {
            connectionPool.removeConnection(connection);

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

    Connection connection = connectionPool.getConnection();

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

                String timestamp = resultSet.getString("timestamp");

                if (metrics.equals("Max") || metrics.equals("Min") || metrics.equals("Avg") || metrics.equals("CPU") || metrics.equals("Memory") || metrics.equals("Disk"))
                {
                  jsonNode.withArray(metrics).add(data).add(timestamp);
                }
                else if (metrics.equals("Loss") || metrics.equals("Uptime") || metrics.equals("BpsValue") || metrics.equals("SNMPData"))
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

              JsonArray username = new JsonArray();

              JsonArray password = new JsonArray();

              JsonArray ipArray = new JsonArray();

              JsonArray idArray = new JsonArray();

              HashMap<String, String> Uptime = new HashMap<>();

              HashMap<String, String> BpsValue = new HashMap<>();

              while (resultSet.next())
              {
                JsonObject rowData = new JsonObject();

                if (message.containsKey("address"))
                {
                  String ip = resultSet.getString("IP");

                  int id = resultSet.getInt("ID");

                  ipArray.add(ip);

                  idArray.add(id);

                  if(message.getString("address").equals("SSH"))
                  {
                    Uptime.put(resultSet.getString("IP"), resultSet.getString("UPTIME"));

                    BpsValue.put(resultSet.getString("IP"), resultSet.getString("BPSVALUE"));

                    JsonObject credential = new JsonObject(resultSet.getString("CREDENTIAL"));

                    username.add(credential.getString("credential_userName"));

                    password.add(credential.getString("credential_password"));
                  }
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

                if(message.getString("address").equals("SSH"))
                {
                  monitorData.put("userName", username);

                  monitorData.put("password", password);

                  monitorData.put("Uptime", Uptime);

                  monitorData.put("BpsValue", BpsValue);
                }

                monitorData.put("ip", ipArray);

                monitorData.put("id", idArray);

                resultMonitorData.put(1, monitorData);

                blockingPromise.complete(resultMonitorData);
              }

              else
              {
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
            connectionPool.removeConnection(connection);

            HashMap<Integer, JsonObject> resultData = result.result();

            promise.complete(resultData);
          }
          else
          {
            connectionPool.removeConnection(connection);

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

    Connection connection = connectionPool.getConnection();

    if(connection != null)
    {
      String query = GenricQuery.getQuery(message);

      vertx.executeBlocking(blockingPromise ->
      {
        try(PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
          ResultSet queryResult = preparedStatement.executeQuery();

          JsonObject pollingData = new JsonObject();

          JsonObject maxObj = new JsonObject();

          JsonObject cpuObj = new JsonObject();

          JsonObject memoryObj = new JsonObject();

          JsonObject diskObj = new JsonObject();

          String successCount = null;

          String failCount = null;

          String unknownCount = null;

          while (queryResult.next())
          {
            String metric = queryResult.getString("metric");

            successCount = Integer.toString(queryResult.getInt("success_count"));

            failCount = Integer.toString(queryResult.getInt("failed_count"));

            unknownCount = Integer.toString(queryResult.getInt("unknown_count"));

            String ip = queryResult.getString("ip");

            String value = queryResult.getString("data");

            if (metric.equals("Max"))
            {
              maxObj.put(ip, value);
            }
            else if (metric.equals("CPU"))
            {
              cpuObj.put(ip, value);
            }
            else if (metric.equals("Memory"))
            {
              memoryObj.put(ip, value);
            }
            else if (metric.equals("Disk"))
            {
              diskObj.put(ip, value);
            }
          }

          pollingData.put("max", maxObj);

          pollingData.put("cpu", cpuObj);

          pollingData.put("memory", memoryObj);

          pollingData.put("disk", diskObj);

          pollingData.put("success", successCount);

          pollingData.put("failed", failCount);

          pollingData.put("unknown", unknownCount);

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
          connectionPool.removeConnection(connection);

          promise.complete(result.result());
        }
        else
        {
          connectionPool.removeConnection(connection);

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
      if(connectionpoolChecker)
      {
        JsonObject getPollingData = new JsonObject();

        getPollingData.put(ConstVariables.ACTION, "get");

        getPollingData.put(ConstVariables.TABLENAME, "");

        getPollingData.put(ConstVariables.COLUMNS, "m.metric, m.data, m.ip, n.success_count, n.failed_count, n.unknown_count");

        getPollingData.put(ConstVariables.CONDITION, ConstVariables.POLLINGCONDITION);

        vertx.setPeriodic(300_000, id -> {

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
        startPromise.fail("Database Operation verticle failed due to not able to create connection pool");
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
      connectionPool.closeConnections();
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());
    }
  }
}
