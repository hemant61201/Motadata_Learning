package LightNMS;

public class ConstVariables
{
  public static final String GET = "get";

  public static final String ADD = "add";

  public static final String DELETE = "delete";
  public static final String UPDATE = "update";

  public static final int POOLCAPACITY = 10;

  public static final String DISCOVERY = "runDiscovery";

  public static final String DATABASE = "database";

  public static String workingDir = System.getProperty("user.dir");

  public static final String BOOTSTRAPPATH = workingDir + "/src/main/resources/bootstrap";

  public static final String JDBCPATH = workingDir + "/src/main/resources/jdbcAuthentication";

  public static final String AUTHPATH = "user.properties";

  public static final String ADDRESS = "address";

  public static final String ACTION = "action";

  public static final String TABLENAME = "tableName";

  public static final String COLUMNS = "columns";

  public static final String PARAMVALUES = "paramValues";

  public static final String CONDITION = "condition";


  public static final String POLLINGCONDITION = "(( SELECT 'Max' AS metric, p.data, p.ip FROM polling_table p WHERE p.metrics = 'Max' AND p.timestamp >= NOW() - INTERVAL '86400' SECOND ORDER BY p.data DESC LIMIT 5) UNION ALL (SELECT 'CPU' AS metric, p.data, p.ip FROM polling_table p WHERE p.metrics = 'CPU' AND p.timestamp >= NOW() - INTERVAL '86400' SECOND ORDER BY p.data DESC LIMIT 5) UNION ALL (SELECT 'Memory' AS metric, p.data, p.ip FROM polling_table p WHERE p.metrics = 'Memory' AND p.timestamp >= NOW() - INTERVAL '86400' SECOND ORDER BY p.data DESC LIMIT 5) UNION ALL (SELECT 'Disk' AS metric, p.data, p.ip FROM polling_table p WHERE p.metrics = 'Disk' AND p.timestamp >= NOW() - INTERVAL '86400' SECOND ORDER BY p.data DESC LIMIT 5) ) AS m CROSS JOIN ( SELECT COUNT(CASE WHEN status = 'success' THEN 1 END) AS success_count, COUNT(CASE WHEN status = 'failed' THEN 1 END) AS failed_count, COUNT(CASE WHEN status = 'unknown' THEN 1 END) AS unknown_count FROM monitor_table ) AS n";

  public static final String PINGCONDITION = " p WHERE p.ip = ? AND p.deviceType = ? AND (( p.metrics IN ('Loss') AND p.timestamp = ( SELECT MAX(sub.timestamp) FROM polling_table sub WHERE sub.metrics = p.metrics AND sub.ip = p.ip AND sub.deviceType = p.deviceType)) OR (p.metrics IN ('Min', 'Max', 'Avg', 'Status') AND p.timestamp >= NOW() - INTERVAL '86400' SECOND)) ORDER BY p.metrics";

  public static final String SSHCONDITION = " p WHERE p.ip = ? AND p.deviceType = ? AND (( p.metrics IN ('Loss', 'Uptime', 'BpsValue') AND p.timestamp = ( SELECT MAX(sub.timestamp) FROM polling_table sub WHERE sub.metrics = p.metrics AND sub.ip = p.ip AND sub.deviceType = p.deviceType)) OR (p.metrics IN ('Min', 'Max', 'Avg', 'CPU', 'Memory', 'Disk', 'Status') AND p.timestamp >= NOW() - INTERVAL '86400' SECOND)) ORDER BY p.metrics";
}
