package LightNMS.Database;

public class Queries
{
  public static String query;
  public static String insertQuery(String tableName)
  {
    switch (tableName)
    {
      case "DiscoveryTable":
      {
        query = "INSERT INTO " + tableName + " (DEVICENAME, IP, DEVICETYPE, STATUS, CREDENTIAL) SELECT ?, ?, ?, ?, ? FROM dual WHERE NOT EXISTS ( SELECT 1 FROM DiscoveryTable WHERE IP = ? AND DEVICETYPE = ?)";

        break;
      }

      case "MonitorTable":
      {
        query = "INSERT INTO " + tableName + " (DEVICENAME, IP, DEVICETYPE, STATUS, CREDENTIAL) SELECT ?, ?, ?, ?, ? FROM dual WHERE NOT EXISTS ( SELECT 1 FROM MonitorTable WHERE IP = ? AND DEVICETYPE = ?)";


        break;
      }

      case "PollingTable":
      {
        query = "INSERT INTO " + tableName + " (metrics, data, ip, timestamp) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        break;
      }
    }

    return query;
  }

  public static String getQuery(String tableName)
  {
    String[] table = new String[2];

    boolean flag = false;

    if(tableName.contains("_"))
    {
      table = tableName.split("_");

      tableName = table[0];

      flag = true;
    }

    switch (tableName)
    {
      case "DiscoveryTable":
      {
        query = "select id,deviceName,ip,deviceType,status from " + tableName;

        break;
      }

      case "MonitorTable":
      {
        query = "SELECT IP, ID FROM " + tableName + " WHERE DEVICETYPE = ?";

        break;
      }

      case "PollingTable":
      {
        break;
      }
    }

    if(flag)
    {
      if(tableName.equals("MonitorTable"))
      {
        query = "select id,deviceName,ip,deviceType,status from " + tableName;

        System.out.println(query);
      }
      else
      {
        query = "select * from " + tableName + " where id = ?";

        System.out.println(query);
      }
      return query;
    }

    return query;
  }

  public static String deleteQuery(String tableName)
  {
    switch (tableName)
    {
      case "DiscoveryTable":
      {
        query = "delete from " + tableName + " where id = ?";

        break;
      }

      case "MonitorTable":
      {
        query = "delete from " + tableName + " where id = ?";

        break;
      }

      case "PollingTable":
      {
        break;
      }
    }

    return query;
  }

  public static String updateQuery(String tableName)
  {
    String[] table = new String[2];

    boolean flag = false;

    if(tableName.contains("_"))
    {
      table = tableName.split("_");

      tableName = table[0];

      flag = true;
    }

    switch (tableName)
    {
      case "DiscoveryTable":
      {
        query = "update " + tableName + " set Status = ? where id = ?";

        break;
      }

      case "MonitorTable":
      {
        query = "update " + tableName + " set Status = ? where id = ?";

        break;
      }

      case "PollingTable":
      {
        break;
      }
    }

    if(flag)
    {
      query = "update " + tableName + " set " + table[1] + " = ?, status = ? where id = ?";

      System.out.println(query);

      return query;
    }

    return query;
  }
}
