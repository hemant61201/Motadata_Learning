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
  private String id;
  private HashMap<Integer, JsonObject> discoveryDataTable = new HashMap<>();

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

          query = Queries.getQuery(queryAddress[1]);

          selectData(connection, query, promise);

          break;

        case "delete":

          query = Queries.deleteQuery(queryAddress[1]);

          deleteData(connection, query, promise);

          break;
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

        preparedStatement.setString(1, jsonObject.getString("deviceName"));

        preparedStatement.setString(2, jsonObject.getString("ip"));

        preparedStatement.setString(3, jsonObject.getString("deviceType"));

        preparedStatement.setString(4, "Unknown");

        preparedStatement.setObject(5, jsonObject.getJsonObject("credential").toString());

        int updatedRow = preparedStatement.executeUpdate();

        System.out.println("Records inserted Successfully " + updatedRow);

        blockingPromise.complete(updatedRow);

      }

      catch (Exception exception)
      {
        blockingPromise.fail(exception);
      }

    }).onComplete(result ->
    {
      if (result.succeeded())
      {
        updatedRow = result.result().toString();

        promise.complete(updatedRow);

      }

      else
      {
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

      try
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

      catch (Exception exception)
      {
        blockingPromise.fail(exception);
      }

    }).onComplete(result ->
    {
      if (result.succeeded())
      {
        HashMap<Integer, JsonObject> resultData = result.result();

        promise.complete(resultData);
      }

      else
      {
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

        preparedStatement.setString(1, id);

        int updatedRow = preparedStatement.executeUpdate();

        System.out.println("Records deleted Successfully " + updatedRow);

        blockingPromise.complete(updatedRow);

      }

      catch (Exception exception)
      {
        blockingPromise.fail(exception);
      }

    }).onComplete(result ->
    {
      if (result.succeeded())
      {
        updatedRow = result.result().toString();

        promise.complete(updatedRow);
      }

      else
      {
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

      vertx.eventBus().consumer("get_DiscoveryTable", message ->
      {
        String address = message.address();

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

      vertx.eventBus().consumer("delete_DiscoveryTable", message ->
      {
        String address = message.address();

        id = message.body().toString();

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

      startPromise.complete();
    }

    catch (Exception exception)
    {
      System.out.println(exception.getMessage());

      startPromise.fail(exception);
    }
  }
}
