package LightNMS.Database;

import LightNMS.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class Connectionpool
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
  private static LinkedBlockingQueue<Connection> connectionQueue;

  private static final ArrayList<Connection> ACTIVECONNECTION = new ArrayList<>();

  private static final int POOLCAPACITY = 10;

  // private
  public static Properties authentication()
  {
    Properties auth_Properties = new Properties();

    try(FileInputStream fileInputStream = new FileInputStream("src/main/resources/jdbcAuthentication"))
    {
      auth_Properties.load(fileInputStream);
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());
    }

    return auth_Properties;
  }

  public static boolean createConnection()
  {
    boolean isCreateConnectionSucess = true;

    Connection connection;

    connectionQueue = new LinkedBlockingQueue<>(POOLCAPACITY);

    // null check for properties
    Properties auth_Properties = authentication();

    if(auth_Properties == null)
    {
      isCreateConnectionSucess = false;
    }

    if(isCreateConnectionSucess)
    {
      try
      {
        for (int i =0 ; i< POOLCAPACITY; i++)
        {
          connection = DriverManager.getConnection(auth_Properties.getProperty("url"), auth_Properties.getProperty("user"), auth_Properties.getProperty("password"));

          connectionQueue.add(connection);
        }
      }

      catch (Exception exception)
      {
        isCreateConnectionSucess = false;

        exception.printStackTrace();
      }

      return isCreateConnectionSucess;
    }

    else
    {
      LOGGER.error("Create Connection Failed");
    }

    return isCreateConnectionSucess;
  }

  public static Connection getConnection ()
  {
    Connection connection = null;
    try
    {
      connection = connectionQueue.take();

      ACTIVECONNECTION.add(connection);
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());
    }

    return connection;
  }

  public static void removeConnection (Connection connection)
  {
    try
    {
      if(connection != null)
      {
        connectionQueue.put(connection);

        ACTIVECONNECTION.remove(connection);
      }

      else
      {
        LOGGER.error("Connection not removed null Connection");
      }
    }

    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }

  public static void closeConnections()
  {
    for(int index=0; index<ACTIVECONNECTION.size(); index++)
    {
      try
      {
        ACTIVECONNECTION.get(index).close();
      }
      catch (Exception exception)
      {
        exception.printStackTrace();
      }
    }
  }
}
