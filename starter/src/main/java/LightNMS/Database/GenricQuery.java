package LightNMS.Database;

import LightNMS.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenricQuery
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
  public static String getQuery(String message)
  {
    if(message != null)
    {
      String[] queryAddress;

      queryAddress = message.split("_");

      switch (queryAddress[0])
      {
        case "add":
        {
          String query = null;

          String commonQuery = "INSERT INTO " + queryAddress[1] + " (DEVICENAME, IP, DEVICETYPE, STATUS, CREDENTIAL) SELECT ?, ?, ?, ?, ? FROM dual WHERE NOT EXISTS ( SELECT 1 FROM " + queryAddress[1] + " WHERE IP = ? AND DEVICETYPE = ?)";

          switch (queryAddress[1])
          {
            case "DiscoveryTable":

              query = commonQuery;

              break;

            case "MonitorTable":

              query = "INSERT INTO " + queryAddress[1] + " (DEVICENAME, IP, DEVICETYPE, STATUS, CREDENTIAL) SELECT ?, ?, ?, ?, ? FROM dual WHERE NOT EXISTS ( SELECT 1 FROM " + queryAddress[1] + " WHERE IP = ? AND DEVICETYPE = ?)";

              break;

            case "PollingTable":

              query = "INSERT INTO " + queryAddress[1] + " (metrics, data, ip, timestamp) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

              break;
          }

          return query;
        }

        case "get":
        {
          if (queryAddress.length == 3)
          {
            String query;

            if (queryAddress[1].equals("MonitorTable"))
            {
              query = "SELECT IP, ID FROM " + queryAddress[1] + " WHERE DEVICETYPE = ?";
            }

            else
            {
              query = "select * from " + queryAddress[1] + " where id = ?";
            }

            return query;
          }

          else
          {
            String query = null;

            String commonQuery = "select id,deviceName,ip,deviceType,status from " + queryAddress[1];

            switch (queryAddress[1])
            {
              case "DiscoveryTable":

                query = commonQuery;

                break;

              case "MonitorTable":

                query = "select id,deviceName,ip,deviceType,status from " + queryAddress[1];

                break;

              case "PollingTable":

                query = "SELECT p.metrics, p.data, p.ip, p.timestamp FROM " + queryAddress[1] + " p WHERE (metrics IN ('Loss', 'Status') AND timestamp = (SELECT MAX(timestamp) FROM " + queryAddress[1] + " WHERE metrics = p.metrics)) OR (metrics IN ('Min', 'Max', 'Avg') AND timestamp >= NOW() - INTERVAL '86400' SECOND AND ip = ?) OR (metrics = 'Status' AND timestamp >= NOW() - INTERVAL '86400' SECOND AND ip = ?) ORDER BY p.metrics";

                break;
            }

            return query;
          }
        }

        case "delete":
        {
          String query = null;

          if (queryAddress[1].equals("DiscoveryTable"))
          {
            query = "delete from " + queryAddress[1] + " where id = ?";
          }

          else if (queryAddress[1].equals("MonitorTable"))
          {
            query = "delete from " + queryAddress[1] + " where id = ?";
          }

          return query;
        }

        case "update":
        {
          if (queryAddress.length == 3)
          {
            String query = null;

            if (queryAddress[1].equals("DiscoveryTable"))
            {
              query = "update " + queryAddress[1] + " set " + queryAddress[2] + " = ?, status = ? where id = ?";
            }

            else if (queryAddress[1].equals("MonitorTable"))
            {
              query = "update " + queryAddress[1] + " set " + queryAddress[2] + " = ?, status = ? where id = ?";
            }

            return query;
          }

          else
          {
            String query = null;

            if (queryAddress[1].equals("DiscoveryTable"))
            {
              query = "update " + queryAddress[1] + " set Status = ? where id = ?";
            }

            else if (queryAddress[1].equals("MonitorTable"))
            {
              query = "update " + queryAddress[1] + " set Status = ? where id = ?";
            }

            return query;
          }
        }
      }
    }

    else
    {
      LOGGER.error("Message is Null");
    }

    return null;
  }

  public static String livePollingData()
  {
    String pollingQuery = "SELECT m.metric, m.data, m.ip, n.success_count, n.failed_count, n.unknown_count FROM (( SELECT 'Max' AS metric, p.data, p.ip FROM pollingtable p WHERE p.metrics = 'Max' AND p.timestamp >= NOW() - INTERVAL '86400' SECOND ORDER BY p.data DESC LIMIT 10) UNION ALL ( SELECT 'Min' AS metric, p.data, p.ip FROM pollingtable p WHERE p.metrics = 'Min' AND p.timestamp >= NOW() - INTERVAL '86400' SECOND ORDER BY p.data ASC LIMIT 10)) AS m CROSS JOIN ( SELECT COUNT(CASE WHEN status = 'success' THEN 1 END) AS success_count, COUNT(CASE WHEN status = 'failed' THEN 1 END) AS failed_count, COUNT(CASE WHEN status = 'Unknown' THEN 1 END) AS unknown_count FROM monitortable) AS n";

    return pollingQuery;
  }
}

