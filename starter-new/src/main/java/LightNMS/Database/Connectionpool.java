package LightNMS.Database;

import LightNMS.ConstVariables;
import LightNMS.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

public class Connectionpool
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
  private static ArrayBlockingQueue<Connection> connectionQueue;

  private static final ArrayList<Connection> ACTIVECONNECTION = new ArrayList<>();

  private static final int POOLCAPACITY = ConstVariables.POOLCAPACITY;

  private static Connectionpool instance;

  private Connectionpool() {}

  public static synchronized Connectionpool getInstance() {
    if (instance == null) {
      instance = new Connectionpool();
    }
    return instance;
  }

  // private
  private static Properties authentication()
  {
    Properties auth_Properties = null;

    try(FileInputStream fileInputStream = new FileInputStream(ConstVariables.JDBCPATH))
    {
      auth_Properties = new Properties();

      auth_Properties.load(fileInputStream);
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());
    }

    return auth_Properties;
  }

  public boolean createConnection()
  {
    boolean isCreateConnectionSucess = true;

    Connection connection;

    connectionQueue = new ArrayBlockingQueue<>(POOLCAPACITY, true);

    // null check for properties
    Properties auth_Properties = authentication();

    if(auth_Properties != null)
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
    }
    else
    {
      isCreateConnectionSucess = false;

      LOGGER.error("Create Connection Failed");
    }

    return isCreateConnectionSucess;
  }

  public Connection getConnection ()
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

  public void removeConnection (Connection connection)
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
      LOGGER.error(exception.getMessage());
    }
  }

  public void closeConnections()
  {
    for (Connection connection : connectionQueue)
    {
      try
      {
        connection.close();

        LOGGER.info("Connection Closed");
      }
      catch (Exception exception)
      {
        LOGGER.error(exception.getMessage());
      }
    }
  }
}
