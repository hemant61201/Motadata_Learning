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

}
