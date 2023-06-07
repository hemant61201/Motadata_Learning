package LightNMS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.h2.util.json.JSONArray;
import org.h2.util.json.JSONObject;

import java.io.FileInputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class QueryService extends AbstractVerticle
{
  public static PreparedStatement preparedStatement = null;

  public static JsonObject jsonObject = null;

  public static JsonObject reply;

  public static String id;

  public static HashMap<Integer,JsonObject> discoveryTable = new HashMap<>();

  public static Object connectionConfig(String message)
  {
    Properties auth_Properties = new Properties();

    try(FileInputStream fileInputStream = new FileInputStream("/home/hemant/Music/LightNMS/src/main/resources/jdbcAuthentication"))
    {
      auth_Properties.load(fileInputStream);
    }

    catch (Exception exception)
    {
      exception.printStackTrace();
    }

    try(Connection connection = DriverManager.getConnection(auth_Properties.getProperty("url"),auth_Properties.getProperty("user"),auth_Properties.getProperty("password")))
    {
      if(message.equals("addDiscovery"))
      {
        return insertData(connection);
      }
      else if (message.equals("getDiscovery"))
      {
        return selectData(connection, "getDiscovery");
      }
      else if (message.equals("deleteDiscovery"))
      {
        return deleteData(connection);
      }
    }

    catch (Exception exception)
    {
      System.out.println("Connection refused");
    }

    return null;
  }

  public static int insertData(Connection connection)
  {
    try
    {
      preparedStatement = connection.prepareStatement("insert into DISCOVERYTABLE (DEVICENAME, IP, DEVICETYPE, STATUS, CREDENTIAL) values(?,?,?,?,?)");

      preparedStatement.setString(1, jsonObject.getString("deviceName"));

      preparedStatement.setString(2, jsonObject.getString("ip"));

      preparedStatement.setString(3,jsonObject.getString("deviceType"));

      preparedStatement.setString(4, "Unknown");

      preparedStatement.setObject(5, jsonObject.getJsonObject("credential").toString());

      int updatedRow = preparedStatement.executeUpdate();

      System.out.println("Records inserted Successfully " + updatedRow);

      if(updatedRow == 1)
      {
        selectData(connection, "addDeviceDatatable");
      }

      return updatedRow;
    }

    catch (Exception exception)
    {
      exception.printStackTrace();
    }

    return 0;
  }

  public static Object selectData(Connection connection, String msg)
  {
    try
    {
      if(msg.equals("getDiscovery"))
      {
        String getDiscoveryQuery = "select id,deviceName,ip,deviceType,status from DISCOVERYTABLE";

        preparedStatement = connection.prepareStatement(getDiscoveryQuery);

        ResultSet getDiscoveryTableSet = preparedStatement.executeQuery();

        ResultSetMetaData resultSetMetaData = getDiscoveryTableSet.getMetaData();

        int columnCount = resultSetMetaData.getColumnCount();

        Integer rows = 1;

        while (getDiscoveryTableSet.next())
        {
          reply = new JsonObject();

          for (int i = 1; i <= columnCount; i++)
          {
            String columnName = resultSetMetaData.getColumnName(i);

            Object columnValue = getDiscoveryTableSet.getObject(i);

            reply.put(columnName, columnValue);
          }

          discoveryTable.put(rows,reply);

          rows++;
        }

        return discoveryTable;
      }

      else if (msg.equals("addDeviceDatatable"))
      {
        reply = new JsonObject();

        System.out.println("ip : " + jsonObject.getString("ip"));

        String query = "select id,deviceName,ip,deviceType,status from DISCOVERYTABLE where ip = ?";

        preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1,jsonObject.getString("ip"));

        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        int columnCount = resultSetMetaData.getColumnCount();

        if (resultSet.next())
        {
          for (int i = 1; i <= columnCount; i++)
          {
            String columnName = resultSetMetaData.getColumnName(i);

            Object columnValue = resultSet.getObject(i);

            reply.put(columnName, columnValue);
          }
        }
      }
    }

    catch (Exception exception)
    {
      exception.printStackTrace();
    }
    return null;
  }

  public static int deleteData(Connection connection)
  {
    try
    {
      preparedStatement = connection.prepareStatement("delete from DISCOVERYTABLE where id = ?");

      preparedStatement.setString(1, id);

      int updatedRow = preparedStatement.executeUpdate();

      System.out.println("Records deleted Successfully " + updatedRow);

      return updatedRow;
    }

    catch (Exception exception)
    {
      exception.printStackTrace();
    }

    return 0;
  }


  @Override
  public void start(Promise<Void> startPromise)
  {
    try
    {
      vertx.eventBus().consumer("add_DiscoveryTable", message ->
      {
        jsonObject = (JsonObject) message.body();

        System.out.println(jsonObject.getString("ip"));

        String updatedRow = String.valueOf(connectionConfig("addDiscovery"));

        if(updatedRow.equals("1"))
        {
          try
          {
            message.reply(reply);
          }

          catch (Exception exception)
          {
            exception.printStackTrace();
          }

        }

        else
        {
          message.reply("Not Updated");
        }
      });

      vertx.eventBus().consumer("get_DiscoveryTable", message ->
      {
        HashMap<Integer,JsonObject> tableMap = (HashMap<Integer, JsonObject>) connectionConfig("getDiscovery");

        JsonArray jsonArray = new JsonArray();

        for (Map.Entry<Integer, JsonObject> entry : tableMap.entrySet())
        {
          Integer key = entry.getKey();

          JsonObject jsonObject = entry.getValue();

          jsonArray.add(jsonObject.encode());
        }
        message.reply(jsonArray);
      });

      vertx.eventBus().consumer("delete_DiscoveryTable", message ->
      {
        id = message.body().toString();

        System.out.println("id : " + id);

        String updatedRow = String.valueOf(connectionConfig("deleteDiscovery"));

        if(updatedRow.equals("1"))
        {
          try
          {
            message.reply("success");
          }

          catch (Exception exception)
          {
            exception.printStackTrace();
          }

        }

        else
        {
          message.reply("Not Updated");
        }
      });
    }

    catch (Exception exception)
    {
      System.out.println(exception.getMessage());
    }
  }
}
