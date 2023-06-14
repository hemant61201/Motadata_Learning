package LightNMS;

import LightNMS.Database.Connectionpool;
import LightNMS.Database.MetricsData;
import LightNMS.Database.Queries;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//TODO remove unused imports
public class CrudOperations extends AbstractVerticle
{
  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  private PreparedStatement preparedStatement = null;
  private String[] queryAddress = new String[2];
  private JsonObject jsonObject = null;
  private String updatedRow;
  private Connectionpool connectionPool = new Connectionpool();
  private int id;
  private String discoveryStatus;

  private ArrayList<String> monitorStatus;

  private ArrayList<Integer> monitorIds;

  private String type;

  private String table;

  private String pollingData;

  private String addTable;

  private String fieldValue;

  private String viewip;

  private void connectionConfig(String message, Promise<Object> promise)
  {
    try
    {
      Connection connection = connectionPool.getConnection();

      queryAddress = message.split("_");

      String query;

      switch (queryAddress[0])
      {
        case "add":

          query = Queries.insertQuery(queryAddress[1]);

          insertData(connection, query, promise);

          break;

        case "get":

          if(queryAddress.length == 3)
          {
            query = Queries.getQuery(queryAddress[1] + "_" + queryAddress[2]);

            if(queryAddress[1].equals("MonitorTable"))
            {
              selectData(connection, query,promise);
            }

            else
            {
              getSingleData(connection, query, promise);
            }
          }

          else
          {
            query = Queries.getQuery(queryAddress[1]);

            selectData(connection, query, promise);
          }

          break;

        case "delete":

          query = Queries.deleteQuery(queryAddress[1]);

          deleteData(connection, query, promise);

          break;

        case "update":

          if(queryAddress.length == 3)
          {
            query = Queries.updateQuery(queryAddress[1] + "_" + queryAddress[2]);

            updateData(connection, query, promise);
          }

          else
          {
            query = Queries.updateQuery(queryAddress[1]);

            updateStatus(connection, query, promise);
          }
      }
    }

    catch (Exception exception)
    {
      System.out.println("Connection refused");

      promise.fail(exception);
    }
  }

  private void insertData(Connection connection, String query, Promise<Object> promise)
  {
    vertx.<Integer>executeBlocking(blockingPromise ->
    {
      try
      {
        if(pollingData != null && addTable.equals("PollingTable"))
        {
          preparedStatement = connection.prepareStatement(query);

          JsonObject jsonDataObj = new JsonObject(pollingData);

          for (String id : jsonDataObj.fieldNames())
          {
            JsonObject metricData = jsonDataObj.getJsonObject(id);

            String pollingIp = metricData.getString("IP");

            for (String metric : metricData.fieldNames())
            {
              if (!metric.equals("ID") && !metric.equals("IP"))
              {
                String value = metricData.getString(metric);

                preparedStatement.setString(1, metric);

                preparedStatement.setString(2, value);

                preparedStatement.setString(3, pollingIp);

                preparedStatement.addBatch();
              }
            }
          }
          preparedStatement.executeBatch();

          blockingPromise.complete();
        }

        else
        {
          preparedStatement = connection.prepareStatement(query);

          preparedStatement.setString(1, jsonObject.getString("DEVICENAME"));

          preparedStatement.setString(2, jsonObject.getString("IP"));

          preparedStatement.setString(3, jsonObject.getString("DEVICETYPE"));

          preparedStatement.setString(4, "Unknown");

          if(table.equals("MonitorTable"))
          {
            preparedStatement.setObject(5, jsonObject.getString("CREDENTIAL").toString());
          }

          else
          {
            preparedStatement.setObject(5, jsonObject.getJsonObject("CREDENTIAL").toString());
          }

          preparedStatement.setString(6,jsonObject.getString("IP"));

          preparedStatement.setString(7, jsonObject.getString("DEVICETYPE"));

          int updatedRow = preparedStatement.executeUpdate();

          blockingPromise.complete(updatedRow);
        }
      }

      catch (Exception exception)
      {
        exception.printStackTrace();

        logger.error(exception.getMessage());

        blockingPromise.fail(exception);
      }

    }, false).onComplete(result ->
    {
      if (result.succeeded())
      {
        connectionPool.removeConnection(connection);

        if(result.result() == null)
        {
          promise.complete();
        }

        else
        {
          updatedRow = result.result().toString();

          promise.complete(updatedRow);
        }

      }

      else
      {
        connectionPool.removeConnection(connection);

        Throwable cause = result.cause();

        promise.fail(cause);
      }
    });
  }

  private void selectData(Connection connection, String query, Promise<Object> promise)
  {
    vertx.<HashMap<Integer, JsonObject>>executeBlocking(blockingPromise ->
    {
      try
      {
        if(table.equals("MonitorTable"))
        {
          HashMap<Integer, JsonObject> monitorDataTable = new HashMap<>();

          JsonObject monitorData;

          Connection connection1 = connectionPool.getConnection();

          PreparedStatement prepareStatement = connection1.prepareStatement(query);

          prepareStatement.setString(1, type);

          ResultSet resultSet = prepareStatement.executeQuery();

          monitorData = new JsonObject();

          monitorData.put("type", type);

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

          monitorDataTable.put(1, monitorData);

          connectionPool.removeConnection(connection1);

          blockingPromise.complete(monitorDataTable);
        }

        else if(table.equals("PollingTable"))
        {
          HashMap<Integer, JsonObject> pollingData = new HashMap<>();

          preparedStatement = connection.prepareStatement(query);

          preparedStatement.setString(1, viewip);

          preparedStatement.setString(2, viewip);

          ResultSet resultSet = preparedStatement.executeQuery();

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
          if(table.equals("DiscoveryTable"))
          {
            HashMap<Integer, JsonObject> discoveryDataTable = new HashMap<>();

            JsonObject discoveryData;

            preparedStatement = connection.prepareStatement(query);

            ResultSet getDiscoveryTableSet = preparedStatement.executeQuery();

            ResultSetMetaData discoveryTableSetMetaData = getDiscoveryTableSet.getMetaData();

            int columnCount = discoveryTableSetMetaData.getColumnCount();

            Integer rows = 1;

            while (getDiscoveryTableSet.next())
            {
              discoveryData = new JsonObject();

              for (int i = 1; i <= columnCount; i++)
              {
                String columnName = discoveryTableSetMetaData.getColumnName(i);

                Object columnValue = getDiscoveryTableSet.getObject(i);

                discoveryData.put(columnName, columnValue);
              }

              discoveryDataTable.put(rows, discoveryData);

              rows++;
            }

            blockingPromise.complete(discoveryDataTable);
          }

          else
          {
            HashMap<Integer, JsonObject> monitorData = new HashMap<>();

            JsonObject discoveryData;

            preparedStatement = connection.prepareStatement(query);

            ResultSet getDiscoveryTableSet = preparedStatement.executeQuery();

            ResultSetMetaData discoveryTableSetMetaData = getDiscoveryTableSet.getMetaData();

            int columnCount = discoveryTableSetMetaData.getColumnCount();

            Integer rows = 1;

            while (getDiscoveryTableSet.next())
            {
              discoveryData = new JsonObject();

              for (int i = 1; i <= columnCount; i++)
              {
                String columnName = discoveryTableSetMetaData.getColumnName(i);

                Object columnValue = getDiscoveryTableSet.getObject(i);

                discoveryData.put(columnName, columnValue);
              }

              monitorData.put(rows, discoveryData);

              rows++;
            }

            blockingPromise.complete(monitorData);
          }

        }

      }

      catch (Exception exception)
      {
        exception.printStackTrace();

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

        Throwable cause = result.cause();

        promise.fail(cause);
      }
    });
  }

  private void getSingleData(Connection connection, String query, Promise<Object> promise)
  {
    vertx.<HashMap<Integer, JsonObject>>executeBlocking(blockingPromise ->
    {
      try
      {
        preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, id);

        ResultSet getSingleDiscoveryTableSet = preparedStatement.executeQuery();

        ResultSetMetaData discoveryTableSetMetaData = getSingleDiscoveryTableSet.getMetaData();

        int columnCount = discoveryTableSetMetaData.getColumnCount();

        if (getSingleDiscoveryTableSet.next())
        {
          JsonObject discoveryData = new JsonObject();

          for (int i = 1; i <= columnCount; i++)
          {
            String columnName = discoveryTableSetMetaData.getColumnName(i);

            Object columnValue = getSingleDiscoveryTableSet.getObject(i);

            discoveryData.put(columnName, columnValue);
          }

          HashMap<Integer, JsonObject> discoveryDataTable = new HashMap<>();

          discoveryDataTable.put(1, discoveryData);

          blockingPromise.complete(discoveryDataTable);
        }

        else
        {
          blockingPromise.fail("No data found for the specified query");
        }
      }

      catch (Exception exception)
      {
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

        Throwable cause = result.cause();

        promise.fail(cause);
      }
    });
  }

  private void deleteData(Connection connection, String query, Promise<Object> promise)
  {
    vertx.<Integer>executeBlocking(blockingPromise ->
    {
      try
      {
        preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, id);

        int updatedRow = preparedStatement.executeUpdate();

        blockingPromise.complete(updatedRow);

      }

      catch (Exception exception)
      {
        blockingPromise.fail(exception);
      }

    }, false).onComplete(result ->
    {
      if (result.succeeded())
      {
        connectionPool.removeConnection(connection);

        updatedRow = result.result().toString();

        promise.complete(updatedRow);
      }

      else
      {
        connectionPool.removeConnection(connection);

        Throwable cause = result.cause();

        promise.fail(cause);
      }
    });
  }

  private void updateData(Connection connection, String query, Promise<Object> promise)
  {
    vertx.<Integer>executeBlocking(blockingPromise ->
    {
      try
      {
        preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, fieldValue);

        preparedStatement.setString(2, "unknown");

        preparedStatement.setInt(3, id);

        int updatedRow = preparedStatement.executeUpdate();

        blockingPromise.complete(updatedRow);

      }

      catch (Exception exception)
      {
        blockingPromise.fail(exception);
      }

    }, false).onComplete(result ->
    {
      if (result.succeeded())
      {
        connectionPool.removeConnection(connection);

        updatedRow = result.result().toString();

        promise.complete(updatedRow);
      }

      else
      {
        connectionPool.removeConnection(connection);

        Throwable cause = result.cause();

        promise.fail(cause);
      }
    });
  }

  private void updateStatus(Connection connection, String query, Promise<Object> promise)
  {
    vertx.<Integer>executeBlocking(blockingPromise ->
    {
      try
      {
        if(table.equals("MonitorTable"))
        {
          preparedStatement = connection.prepareStatement(query);

          for (int i = 0; i < monitorIds.size(); i++)
          {
            preparedStatement.setString(1, monitorStatus.get(i));

            preparedStatement.setInt(2,monitorIds.get(i));

            preparedStatement.addBatch();
          }

          preparedStatement.executeBatch();

          blockingPromise.complete();
        }

        else
        {
          preparedStatement = connection.prepareStatement(query);

          preparedStatement.setString(1, discoveryStatus);

          preparedStatement.setInt(2,id);

          int updatedRow = preparedStatement.executeUpdate();

          blockingPromise.complete(updatedRow);
        }
      }

      catch (Exception exception)
      {
        exception.printStackTrace();

        blockingPromise.fail(exception);
      }

    }, false).onComplete(result ->
    {
      if (result.succeeded())
      {
        connectionPool.removeConnection(connection);

        if(result.result() == null)
        {
          promise.complete();
        }

        else
        {
          updatedRow = result.result().toString();

          promise.complete(updatedRow);
        }
      }

      else
      {
        connectionPool.removeConnection(connection);

        Throwable cause = result.cause();

        promise.fail(cause);
      }
    });
  }

  private void dashBoardData(Promise<JsonObject> promise)
  {
    Connection connection = connectionPool.getConnection();

    String query = Queries.livePollingData();

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
          exception.printStackTrace();
        }

      }, false).onComplete(result ->
    {
      if(result.succeeded())
      {
        connectionPool.removeConnection(connection);

        promise.complete((JsonObject) result.result());
      }
      else
      {
        connectionPool.removeConnection(connection);

        Throwable cause = result.cause();

        promise.fail(cause);
      }
    });
  }

  @Override
  public void start(Promise<Void> startPromise)
  {
    try
    {
      connectionPool.createConnection();

      vertx.setPeriodic(5000, id -> {

        Promise<JsonObject> promise = Promise.promise();

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

        dashBoardData(promise);
      });

      vertx.eventBus().consumer("database", message ->
      {
        JsonObject json = new JsonObject(message.body().toString());

        String action = json.getString("action");

        String param = json.getString("param");

        switch (action)
        {
          case "add_DiscoveryTable":
          {
            String[] splitAddress = action.split("_");

            addTable = splitAddress[1];

            jsonObject = new JsonObject(param);

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

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

                logger.error("Add operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "add_MonitorTable":
          {
            String[] splitAddress = action.split("_");

            addTable = splitAddress[1];

            table = splitAddress[1];

            jsonObject = new JsonObject(param);

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

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

                logger.error("Add operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "add_PollingTable":
          {
            String[] splitAddress = action.split("_");

            addTable = splitAddress[1];

            pollingData = param;

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

            promise.future().onComplete(result ->
            {
              if (result.succeeded())
              {
                logger.info("Data is added Successfully");
              }
              else
              {
                Throwable cause = result.cause();

                logger.error("Add operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "get_DiscoveryTable":
          {
            String[] splitAddress = action.split("_");

            table = splitAddress[1];

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

            promise.future().onComplete(result ->
            {
              if (result.succeeded())
              {
                HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) result.result();

                JsonArray jsonArray = new JsonArray();

                for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
                {
                  Integer key = entry.getKey();

                  JsonObject jsonObject = entry.getValue();

                  jsonArray.add(jsonObject.encode());
                }

                message.reply(jsonArray);
              }
              else
              {
                Throwable cause = result.cause();

                System.out.println("Get operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "get_DiscoveryTable_id":
          {
            String[] modifyAddress = action.split("_");

            String modifiedAddress = modifyAddress[0] + "_" + modifyAddress[1] + "_" + param;

            id = Integer.parseInt(param);

            Promise<Object> promise = Promise.promise();

            connectionConfig(modifiedAddress, promise);

            promise.future().onComplete(result ->
            {
              if (result.succeeded())
              {
                HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) result.result();

                JsonObject rowData = new JsonObject();

                for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
                {
                  rowData = entry.getValue();
                }

                message.reply(rowData);
              }
              else
              {
                Throwable cause = result.cause();

                logger.error("Get operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "get_MonitorTable_data":
          {
            table = action;

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

            promise.future().onComplete(result ->
            {
              if (result.succeeded())
              {
                HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) result.result();

                JsonArray jsonArray = new JsonArray();

                for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
                {
                  Integer key = entry.getKey();

                  JsonObject jsonObject = entry.getValue();

                  jsonArray.add(jsonObject.encode());
                }

                message.reply(jsonArray);
              }
              else
              {
                Throwable cause = result.cause();

                System.out.println("Get operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "get_MonitorTable":
          {
            String[] splitAddress = action.split("_");

            table = splitAddress[1];

            type = param;

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

            promise.future().onComplete(result ->
            {
              if (result.succeeded())
              {
                HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) result.result();

                JsonObject rowData = new JsonObject();

                for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
                {
                  rowData = entry.getValue();
                }

                message.reply(rowData);
              }
              else
              {
                Throwable cause = result.cause();

                logger.error("Get operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "get_PollingTable":
          {
            String[] splitAddress = action.split("_");

            table = splitAddress[1];

            viewip = param;

            type = param;

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

            promise.future().onComplete(result ->
            {
              if (result.succeeded())
              {
                HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) result.result();

                JsonObject rowData = new JsonObject();

                for (Map.Entry<Integer, JsonObject> entry : data.entrySet())
                {
                  rowData = entry.getValue();
                }

                message.reply(rowData);
              }
              else
              {
                Throwable cause = result.cause();

                logger.error("Get operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "delete_DiscoveryTable":
          {
            id = Integer.parseInt(param);

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

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

                logger.error("Delete operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "delete_MonitorTable":
          {
            id = Integer.parseInt(param);

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

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

                logger.error("Delete operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "update_Discovery":
          {
            String[] splitMsg = param.split("_");

            id = Integer.parseInt(splitMsg[0]);

            if(splitMsg[1].equals("credential"))
            {
              String[] credentialValue = splitMsg[2].split("\\.");

              fieldValue = "{\"credential_userName\":" + "\"" + credentialValue[0] + "\"" + ",\"credential_password\":" + "\"" +credentialValue[1] + "\"}";
            }

            else
            {
              fieldValue = splitMsg[2];
            }

            action += "Table_" + splitMsg[1];

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

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

                logger.error("UpdateStatus operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "update_DiscoveryTable":
          {
            String[] splitMsg = param.split("_");

            discoveryStatus = splitMsg[0];

            String number = splitMsg[1].trim();

            String numberString = number.substring(1, number.length() - 1);

            id = Integer.parseInt(numberString);

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

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

                logger.error("Update operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          case "update_MonitorTable":
          {
            String[] splitAddress = action.split("_");

            table = splitAddress[1];

            JsonObject updateStatusResult = new JsonObject(param);

            monitorIds = new ArrayList<>();

            monitorStatus = new ArrayList<>();

            for (String key : updateStatusResult.fieldNames())
            {
              monitorIds.add(Integer.parseInt(key));

              JsonObject targetObject = updateStatusResult.getJsonObject(key);

              monitorStatus.add(targetObject.getString("Status"));
            }

            Promise<Object> promise = Promise.promise();

            connectionConfig(action, promise);

            promise.future().onComplete(result ->
            {
              if (result.succeeded())
              {
                logger.info("Success_Fully Updated MonitorTable");
              }

              else
              {
                Throwable cause = result.cause();

                logger.error("Update operation failed: " + cause.getMessage());

                message.fail(500, cause.getMessage());
              }
            });
          }
          break;
          default:
            logger.info("Invalid action: " + action);

            message.fail(500, "Invalid action: " + action);
        }
      });

      startPromise.complete();
    }

    catch (Exception exception)
    {
      System.out.println(exception.getMessage());

      startPromise.fail(exception);
    }
  }
}
