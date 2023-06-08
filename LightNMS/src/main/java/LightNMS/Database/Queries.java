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
        query = "insert into " + tableName + " (DEVICENAME, IP, DEVICETYPE, STATUS, CREDENTIAL) values(?,?,?,?,?)";
      }

      case "MonitorTable":
      {

      }

      case "PollingTable":
      {

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
      }

      case "MonitorTable":
      {

      }

      case "PollingTable":
      {

      }
    }

    if(flag)
    {
      query = "select * from " + tableName + " where id = ?";

      System.out.println(query);

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
      }

      case "MonitorTable":
      {

      }

      case "PollingTable":
      {

      }
    }

    return query;
  }

  public static String updateQuery(String tableName)
  {
    switch (tableName)
    {
      case "DiscoveryTable":
      {
        query = "update " + tableName + " set Status = ? where id = ?";
      }

      case "MonitorTable":
      {

      }

      case "PollingTable":
      {

      }
    }

    return query;
  }
}
