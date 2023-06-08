package LightNMS.Database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class Connectionpool
{
  static Properties auth_Properties;

  private static LinkedBlockingQueue<Connection> connectionQueue;

  private static ArrayList<Connection> activeConnections = new ArrayList<>();

  private final int poolCapacity = 7;

  private static Connectionpool connectionPool;

  public Connectionpool(){}

  public static void authentication()
  {
    auth_Properties = new Properties();

    try(FileInputStream fileInputStream = new FileInputStream("/home/hemant/Music/LightNMS/src/main/resources/jdbcAuthentication"))
    {
      auth_Properties.load(fileInputStream);
    }

    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }

  public static Connectionpool getInstance()
  {
    if (connectionPool == null)
    {
      connectionPool = new Connectionpool();
    }

    return connectionPool;
  }

  public void createConnection()
  {
    Connection connection;

    authentication();

    connectionQueue = new LinkedBlockingQueue<>(poolCapacity);

    try
    {
      for (int i =0 ; i< poolCapacity; i++)
      {
        connection = DriverManager.getConnection(auth_Properties.getProperty("url"), auth_Properties.getProperty("user"), auth_Properties.getProperty("password"));

        connectionQueue.add(connection);
      }
    }

    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }

  public Connection getConnection ()
  {
    Connection connection = connectionQueue.poll();

    activeConnections.add(connection);

    return connection;
  }

  public static void removeConnection (Connection connection)
  {
    try
    {
      connectionQueue.put(connection);

      activeConnections.remove(connection);
    }

    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }

  public void closeConnections()
  {
    for(int index=0; index<activeConnections.size(); index++)
    {
      try
      {
        activeConnections.get(index).close();
      }
      catch (Exception exception)
      {
        exception.printStackTrace();
      }
    }
  }
}
