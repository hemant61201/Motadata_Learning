package LightNMS;

import LightNMS.Database.Connectionpool;
import LightNMS.Database.Queries;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CrudOperations extends AbstractVerticle {
  private PreparedStatement preparedStatement = null;
  private String[] queryAddress = new String[2];
  private JsonObject jsonObject = null;
  private String updatedRow;
  private Connectionpool connectionPool = new Connectionpool();
  private int id;
  private String status;

  private String type;

  private String table;

  private String fieldValue;
  private HashMap<Integer, JsonObject> discoveryDataTable = new HashMap<>();

  private HashMap<Integer, JsonObject> monitorDataTable = new HashMap<>();

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

            getSingleData(connection, query, promise);
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

        int updatedRow = preparedStatement.executeUpdate();

        System.out.println("Records inserted Successfully " + updatedRow);

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

  private void selectData(Connection connection, String query, Promise<Object> promise)
  {
    vertx.<HashMap<Integer, JsonObject>>executeBlocking(blockingPromise ->
    {
      JsonObject discoveryData;

      JsonObject monitorData;

      try
      {
        if(table.equals("MonitorTable"))
        {
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

          System.out.println("crudResult : " + monitorData.toString());

          monitorDataTable.put(1, monitorData);

          connectionPool.removeConnection(connection1);

          blockingPromise.complete(monitorDataTable);
        }

        else
        {
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

      }

      catch (Exception exception)
      {
        System.out.println(exception);

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

        System.out.println("Records deleted Successfully " + updatedRow);

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

        System.out.println("Records Updated Successfully " + updatedRow);

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
        System.out.println(query);

        preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, status);

        preparedStatement.setInt(2,id);

        int updatedRow = preparedStatement.executeUpdate();

        System.out.println("Records update Successfully " + updatedRow);

        blockingPromise.complete(updatedRow);

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

  @Override
  public void start(Promise<Void> startPromise)
  {
    try
    {
      connectionPool.createConnection();

      vertx.eventBus().consumer("add_DiscoveryTable", message ->
      {
        String address = message.address();

        jsonObject = (JsonObject) message.body();

        System.out.println("hi" + jsonObject.getString("ip"));

        Promise<Object> promise = Promise.promise();

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

            System.out.println("Add operation failed: " + cause.getMessage());

            message.fail(500, cause.getMessage());
          }
        });

        connectionConfig(address, promise);
      });

      vertx.eventBus().consumer("add_MonitorTable", message ->
      {
        String address = message.address();

        String[] splitAddress = address.split("_");

        table = splitAddress[1];

        jsonObject = (JsonObject) message.body();

        System.out.println("hi" + jsonObject.getString("CREDENTIAL"));

        Promise<Object> promise = Promise.promise();

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

            System.out.println("Add operation failed: " + cause.getMessage());

            message.fail(500, cause.getMessage());
          }
        });

        connectionConfig(address, promise);
      });

      vertx.eventBus().consumer("get_DiscoveryTable", message ->
      {
        String address = message.address();

        String[] splitAddress = address.split("_");

        table = splitAddress[1];

        System.out.println(address);

        Promise<Object> promise = Promise.promise();

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

        connectionConfig(address, promise);
      });

      vertx.eventBus().consumer("get_DiscoveryTable_id", message ->
      {
        String[] modifyAddress = message.address().split("_");

        String address = modifyAddress[0] + "_" + modifyAddress[1] + "_" + message.body().toString();

        id = Integer.parseInt(message.body().toString());

        System.out.println(address);

        Promise<Object> promise = Promise.promise();

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

            System.out.println("Get operation failed: " + cause.getMessage());

            message.fail(500, cause.getMessage());
          }
        });

        connectionConfig(address, promise);
      });

      vertx.eventBus().consumer("get_MonitorTable", message ->
      {
        String address = message.address();

        String[] splitAddress = address.split("_");

        table = splitAddress[1];

        type = message.body().toString();

        System.out.println(address);

        Promise<Object> promise = Promise.promise();

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

            System.out.println("Get operation failed: " + cause.getMessage());

            message.fail(500, cause.getMessage());
          }
        });

        connectionConfig(address, promise);
      });

      vertx.eventBus().consumer("delete_DiscoveryTable", message ->
      {
        String address = message.address();

        id = Integer.parseInt(message.body().toString());

        System.out.println("id : " + id);

        Promise<Object> promise = Promise.promise();

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

            System.out.println("Delete operation failed: " + cause.getMessage());

            message.fail(500, cause.getMessage());
          }
        });

        connectionConfig(address, promise);
      });

      vertx.eventBus().consumer("update_Discovery", message ->
      {
        String address = message.address();

        String[] splitMsg = message.body().toString().split("_");

        id = Integer.parseInt(splitMsg[0]);

        if(splitMsg[1].equals("credential"))
        {
          System.out.println(splitMsg[2]);

          String[] credentialValue = splitMsg[2].split("\\.");

          fieldValue = "{\"credential_userName\":" + "\"" + credentialValue[0] + "\"" + ",\"credential_password\":" + "\"" +credentialValue[1] + "\"}";
        }

        else
        {
          fieldValue = splitMsg[2];
        }

        System.out.println("id : " + id);

        address += "Table_" + splitMsg[1];

        Promise<Object> promise = Promise.promise();

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

            System.out.println("UpdateStatus operation failed: " + cause.getMessage());

            message.fail(500, cause.getMessage());
          }
        });

        connectionConfig(address, promise);
      });

      vertx.eventBus().consumer("update_DiscoveryTable", message ->
      {
        String address = message.address();

        System.out.println(message.body().toString());

        String[] splitMsg = message.body().toString().split("_");

        status = splitMsg[0];

        String number = splitMsg[1].trim();

        String numberString = number.substring(1, number.length() - 1);

        System.out.println(numberString);

        id = Integer.parseInt(numberString);

        System.out.println("id : " + id);

        Promise<Object> promise = Promise.promise();

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

            System.out.println("Update operation failed: " + cause.getMessage());

            message.fail(500, cause.getMessage());
          }
        });

        connectionConfig(address, promise);
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
