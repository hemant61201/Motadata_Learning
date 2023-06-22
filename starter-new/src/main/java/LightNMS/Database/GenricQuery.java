package LightNMS.Database;

import LightNMS.ConstVariables;
import LightNMS.Main;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenricQuery
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
  public static String getQuery(JsonObject message)
  {
    if(message != null)
    {
      switch (message.getString("action"))
      {
        case ConstVariables.ADD:
        {
          return "INSERT INTO " + message.getString("tableName") + " (" + message.getString("columns") + ") " + message.getString("condition");

        }

        case ConstVariables.GET:
        {
          return  "select " + message.getString("columns") + " from " + message.getString("tableName") + message.getString("condition");
        }

        case ConstVariables.DELETE:
        {
          return  "delete from " + message.getString("tableName") + message.getString("condition");
        }

        case ConstVariables.UPDATE:
        {
          return  "update " + message.getString("tableName") + " set " + message.getString("columns") + message.getString("condition");
        }
      }
    }

    else
    {
      LOGGER.error("Message is Null");
    }

    return null;
  }
}

