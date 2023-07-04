package LightNMS;

import com.zaxxer.nuprocess.NuProcess;
import com.zaxxer.nuprocess.NuProcessBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Handler;
import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class PollingExecution extends AbstractVerticle
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private static final ConcurrentHashMap<Integer, JsonArray> sendIps = new ConcurrentHashMap<>();

  private static final ConcurrentHashMap<Integer, JsonObject> upTime = new ConcurrentHashMap<>();

  private static final ConcurrentHashMap<Integer, JsonObject> bpsValue = new ConcurrentHashMap<>();

  private Future<Object> getMonitorData(String action)
  {
    Promise<Object> promise = Promise.promise();

    if(action != null)
    {
      switch (action)
      {
        case "Ping":
        {
          JsonObject getMonitorData = new JsonObject();

          JsonArray paramValues = new JsonArray();

          paramValues.add("Ping");

          getMonitorData.put(ConstVariables.ACTION, "get");

          getMonitorData.put(ConstVariables.TABLENAME, "monitor_table");

          getMonitorData.put(ConstVariables.COLUMNS, "ip,id");

          getMonitorData.put(ConstVariables.ADDRESS, "allIp");

          getMonitorData.put(ConstVariables.PARAMVALUES, paramValues);

          getMonitorData.put(ConstVariables.CONDITION, " WHERE deviceType = ?");

          Future<Object> GetMonitor = DatabaseOperations.executeGetQuery(vertx, getMonitorData);

          if (GetMonitor != null)
          {
            GetMonitor.onComplete(getResult ->
            {
              if (getResult.succeeded())
              {
                @SuppressWarnings("unchecked")
                HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) getResult.result();

                JsonObject getData = new JsonObject();

                for (Map.Entry<Integer, JsonObject> entry : data.entrySet()) {
                  getData = entry.getValue();
                }

                JsonArray ipArray = new JsonArray(getData.getString("ip"));

                JsonArray idArray = new JsonArray(getData.getString("id"));

                JsonObject requestData = new JsonObject()
                  .put("Method", "Polling")
                  .put("Operation", getData.getString("type"))
                  .put("credentialProfile", new JsonObject()
                    .put("username", new JsonArray(){})
                    .put("password", new JsonArray(){}))
                  .put("discoveryProfile", new JsonObject()
                    .put("ip", ipArray)
                    .put("port", 22)
                    .put("id", idArray));

                promise.complete(requestData);
              }
              else
              {
                LOGGER.error("Error: " + getResult.cause());
              }
            });
          }
          else
          {
            LOGGER.error("Error: GetMonitor is null");
          }
        }
        break;

        case "SSH":
        {
          JsonObject getMonitorData = new JsonObject();

          JsonArray paramValues = new JsonArray();

          paramValues.add("SSH");

          getMonitorData.put(ConstVariables.ACTION, "get");

          getMonitorData.put(ConstVariables.TABLENAME, "monitor_table");

          getMonitorData.put(ConstVariables.COLUMNS, "m.IP, m.ID, m.credential, p1.DATA AS Uptime, p2.DATA AS BpsValue");

          getMonitorData.put(ConstVariables.ADDRESS, "SSH");

          getMonitorData.put(ConstVariables.PARAMVALUES, paramValues);

          getMonitorData.put(ConstVariables.CONDITION, " m LEFT JOIN ( SELECT p1.IP, p1.DATA FROM polling_table p1 JOIN ( SELECT IP, MAX(TIMESTAMP) AS latest_timestamp FROM polling_table WHERE METRICS = 'Uptime' GROUP BY IP ) p2 ON p1.IP = p2.IP AND p1.TIMESTAMP = p2.latest_timestamp WHERE p1.METRICS = 'Uptime' ) p1 ON m.IP = p1.IP LEFT JOIN ( SELECT p3.IP, p3.DATA FROM polling_table p3 JOIN ( SELECT IP, MAX(TIMESTAMP) AS latest_timestamp FROM polling_table WHERE METRICS = 'Bytes' GROUP BY IP ) p4 ON p3.IP = p4.IP AND p3.TIMESTAMP = p4.latest_timestamp WHERE p3.METRICS = 'Bytes' ) p2 ON m.IP = p2.IP WHERE deviceType = ?");

          Future<Object> GetMonitor = DatabaseOperations.executeGetQuery(vertx, getMonitorData);

          if (GetMonitor != null)
          {
            GetMonitor.onComplete(getResult ->
            {
              if (getResult.succeeded())
              {
                @SuppressWarnings("unchecked")
                HashMap<Integer, JsonObject> data = (HashMap<Integer, JsonObject>) getResult.result();

                JsonObject getData = new JsonObject();

                for (Map.Entry<Integer, JsonObject> entry : data.entrySet()) {
                  getData = entry.getValue();
                }

                JsonArray ipArray = new JsonArray(getData.getString("ip"));

                sendIps.put(1, new JsonArray(getData.getString("ip")));

                upTime.put(1, getData.getJsonObject("Uptime"));

                bpsValue.put(1, getData.getJsonObject("BpsValue"));

                JsonArray userArray = new JsonArray(getData.getString("userName"));

                JsonArray passwordArray = new JsonArray(getData.getString("password"));

                JsonArray idArray = new JsonArray(getData.getString("id"));

                JsonObject requestData = new JsonObject()
                  .put("Method", "Polling")
                  .put("Operation", getData.getString("type"))
                  .put("credentialProfile", new JsonObject()
                    .put("username", userArray)
                    .put("password", passwordArray))
                  .put("discoveryProfile", new JsonObject()
                    .put("ip", ipArray)
                    .put("port", 22)
                    .put("id", idArray));

                promise.complete(requestData);
              }
              else
              {
                LOGGER.error("Error: " + getResult.cause());
              }
            });
          }
          else
          {
            LOGGER.error("Error: GetMonitor is null");
          }
        }
      }
    }

    return promise.future();
  }

  private void updateStatus(JsonArray params)
  {
    JsonObject updateStatus = new JsonObject();

    updateStatus.put(ConstVariables.ACTION,"update");

    updateStatus.put(ConstVariables.TABLENAME, "monitor_table");

    updateStatus.put(ConstVariables.COLUMNS, "status");

    updateStatus.put(ConstVariables.PARAMVALUES, params);

    updateStatus.put(ConstVariables.CONDITION, " = ? where ip = ? and deviceType = ?");

    Future<Object> updateMonitor = DatabaseOperations.executeBatchQuery(vertx, updateStatus);

    if (updateMonitor != null)
    {
      updateMonitor.onComplete(updateResult ->
      {
        if (updateResult.succeeded())
        {
          LOGGER.info("Execution of Update Monitor Table Successful Completed");
        }
        else
        {
          LOGGER.error("Error: " + updateResult.cause());
        }
      });
    }
    else
    {
      LOGGER.error("Error: UpdateMonitor is null");
    }
  }

  private void addPolling(JsonArray params)
  {
    JsonObject addData = new JsonObject();

    addData.put(ConstVariables.ACTION,"add");

    addData.put(ConstVariables.TABLENAME, "polling_table");

    addData.put(ConstVariables.COLUMNS, "metrics, data, ip, timestamp, deviceType");

    addData.put(ConstVariables.PARAMVALUES, params);

    addData.put(ConstVariables.CONDITION, "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?)");

    Future<Object> AddPolling = DatabaseOperations.executeBatchQuery(vertx, addData);

    if (AddPolling != null)
    {
      AddPolling.onComplete(addResult ->
      {
        if (addResult.succeeded())
        {
          LOGGER.info("Execution of add PollingTable Successful Completed");
        }
        else
        {
          LOGGER.error("Error: " + addResult.cause());
        }
      });
    }
    else
    {
      LOGGER.error("Error: AddPolling is null");
    }
  }

  public static long convertUptimeToSeconds(String uptime)
  {
    uptime = uptime.replace("up ", "");

    String[] parts = uptime.split(", ");

    long months = 0;

    long weeks = 0;

    long days = 0;

    long hours = 0;

    long minutes = 0;

    for (String part : parts)
    {
      if(part.contains("month"))
      {
        months = Long.parseLong(part.split(" ")[0]);
      }

      else if(part.contains("week"))
      {
        weeks = Long.parseLong(part.split(" ")[0]);
      }

      else if (part.contains("days"))
      {
        days = Long.parseLong(part.split(" ")[0]);
      }

      else if (part.contains("hours"))
      {
        hours = Long.parseLong(part.split(" ")[0]);
      }

      else if (part.contains("minutes"))
      {
        minutes = Long.parseLong(part.split(" ")[0]);
      }
    }

    long secondsInMonths = 30 * 24 * 60 * 60;

    long secondsInWeeks = 7 * 24 * 60 * 60;

    long secondsInDay = 24 * 60 * 60;

    long secondsInHour = 60 * 60;

    long secondsInMinute = 60;

    return months * secondsInMonths + weeks * secondsInWeeks + days * secondsInDay + hours * secondsInHour + minutes * secondsInMinute;
  }

  @Override
  public void start(Promise<Void> startPromise)
  {
    try
    {
      vertx.setPeriodic(120_000, timerId ->
      {
        Future<Object> GetMonitor = getMonitorData("Ping");

        if (GetMonitor != null)
        {
          GetMonitor.onComplete(getResult ->
          {
            if (getResult.succeeded())
            {
              executeCommand(ConstVariables.BOOTSTRAPPATH, getResult.result().toString(), exeResult ->
              {
                if (exeResult.succeeded())
                {
                  String[] lines = exeResult.result().split("\\r?\\n");

                  JsonObject finalResult = new JsonObject();

                  for (String line : lines)
                  {
                    String[] keyValuePairs = line.replace("{", "")
                      .replace("}", "")
                      .split(" ");

                    JsonObject entryObject = new JsonObject();

                    for (String pair : keyValuePairs)
                    {
                      String[] parts = pair.split(":");

                      String key = parts[0];

                      String value = parts[1];

                      entryObject.put(key, value);
                    }

                    String id = entryObject.getString("ID");

                    finalResult.put(id, entryObject);
                  }

                  JsonArray batchUpdateParam = new JsonArray();

                  JsonArray batchAddParam = new JsonArray();

                  for (String id : finalResult.fieldNames())
                  {
                    JsonObject metricData = finalResult.getJsonObject(id);

                    JsonArray paramValue = new JsonArray();

                    paramValue.add(metricData.getString("Status"));

                    paramValue.add(metricData.getString("IP"));

                    paramValue.add("Ping");

                    batchUpdateParam.add(paramValue);

                    for (String key : metricData.fieldNames())
                    {
                      if (!key.equals("IP") && !key.equals("ID"))
                      {
                        JsonArray parameter = new JsonArray();

                        parameter.add(key);

                        parameter.add(metricData.getString(key));

                        parameter.add(metricData.getString("IP"));

                        parameter.add("Ping");

                        batchAddParam.add(parameter);
                      }
                    }
                  }

                  updateStatus(batchUpdateParam);

                  addPolling(batchAddParam);
                }
                else
                {
                  System.err.println("Process execution failed: " + exeResult.cause().getMessage());

                  startPromise.fail(exeResult.cause());
                }
              });

            }
            else
            {
              LOGGER.error("Error: " + getResult.cause());
            }
          });
        }
        else
        {
          LOGGER.error("Error: GetMonitor is null");
        }
      });

      vertx.setPeriodic(300_000, timerId ->
      {
        Future<Object> getMonitor = getMonitorData("SSH");

        if (getMonitor != null)
        {
          getMonitor.onComplete(getResult ->
          {
            if (getResult.succeeded())
            {
              executeCommand(ConstVariables.BOOTSTRAPPATH, getResult.result().toString(), exeResult ->
              {
                if (exeResult.succeeded())
                {
                  if(exeResult.result() != null)
                  {
                    JsonArray successResult = new JsonArray(exeResult.result());

                    JsonArray successIp = new JsonArray();

                    JsonArray batchUpdateParam = new JsonArray();

                    JsonArray batchAddParam = new JsonArray();

                    for (int i = 0; i < successResult.size(); i++)
                    {
                      JsonObject metricData = successResult.getJsonObject(i);

                      JsonArray paramValue = new JsonArray();

                      JsonObject fpingData = metricData.getJsonObject("Fping");

                      paramValue.add(fpingData.getString("Status"));

                      paramValue.add(metricData.getString("IP"));

                      successIp.add(metricData.getString("IP"));

                      paramValue.add("SSH");

                      batchUpdateParam.add(paramValue);

                      for (String key : metricData.fieldNames())
                      {
                        if (!key.equals("IP"))
                        {
                          JsonArray parameter = new JsonArray();

                          if (key.equals("Fping"))
                          {
                            for (String keys : fpingData.fieldNames())
                            {
                              JsonArray param = new JsonArray();

                              param.add(keys);

                              param.add(fpingData.getString(keys));

                              param.add(metricData.getString("IP"));

                              param.add("SSH");

                              batchAddParam.add(param);
                            }
                          }

                          else if (key.equals("BpsValue"))
                          {
                            JsonArray parameter1 = new JsonArray();

                            parameter1.add("Bytes");

                            parameter1.add(metricData.getString(key));

                            parameter1.add(metricData.getString("IP"));

                            parameter1.add("SSH");

                            batchAddParam.add(parameter1);

                            parameter.add(key);

                            long oldUptime;

                            String oldBps;

                            JsonObject upTimes = upTime.get(1);

                            JsonObject bpsValues = bpsValue.get(1);

                            if(upTimes.containsKey(metricData.getString("IP")))
                            {
                              if(upTimes.getString(metricData.getString("IP")) != null && bpsValues.getString(metricData.getString("IP")) != null)
                              {
                                oldUptime = convertUptimeToSeconds(upTimes.getString(metricData.getString("IP")));

                                oldBps = bpsValues.getString(metricData.getString("IP"));
                              }

                              else
                              {
                                oldUptime = 0L;

                                oldBps = "";
                              }

                            }

                            else
                            {
                              oldUptime = 0L;

                              oldBps = "";
                            }

                            long newUptime = convertUptimeToSeconds(metricData.getString("Uptime"));

                            long time;

                            if(oldUptime > newUptime)
                            {
                              time = newUptime;
                            }

                            else
                            {
                              time = newUptime - oldUptime;
                            }

                            StringBuilder bpsValue = new StringBuilder();

                            String[] interfaces = metricData.getString(key).substring(0, metricData.getString(key).length() - 1).split(";");

                            String[] oldInterfaces;

                            if(!Objects.equals(oldBps, ""))
                            {
                              oldInterfaces = oldBps.substring(0, oldBps.length() - 1).split(";");
                            }

                            else
                            {
                              oldInterfaces = new String[0];
                            }

                            for(String interfaceData : interfaces)
                            {
                              String name = interfaceData.split(":")[0];

                              String oldInterfaceData = null;

                              for(String oldInterface : oldInterfaces)
                              {
                                if(oldInterface.contains(name))
                                {
                                  oldInterfaceData = oldInterface;
                                }
                              }

                              if(oldInterfaceData != null)
                              {
                                if(name.equals(oldInterfaceData.split(":")[0]))
                                {
                                  long receiveValue = (Long.parseLong(interfaceData.split(":")[1].split(",")[0]) - Long.parseLong(oldInterfaceData.split(":")[1].split(",")[0])) / time;

                                  long transmitValue = (Long.parseLong(interfaceData.split(":")[1].split(",")[1]) - Long.parseLong(oldInterfaceData.split(":")[1].split(",")[1])) / time;

                                  bpsValue.append(name).append(":").append(receiveValue).append(",").append(transmitValue).append(";");
                                }
                              }

                              else
                              {
                                long receiveValue = Long.parseLong(interfaceData.split(":")[1].split(",")[0]) / time;

                                long transmitValue = Long.parseLong(interfaceData.split(":")[1].split(",")[1]) / time;

                                bpsValue.append(name).append(":").append(receiveValue).append(",").append(transmitValue).append(";");
                              }
                            }

                            parameter.add(bpsValue.toString());

                            parameter.add(metricData.getString("IP"));

                            parameter.add("SSH");

                            batchAddParam.add(parameter);
                          }

                          else
                          {
                            parameter.add(key);

                            parameter.add(metricData.getString(key));

                            parameter.add(metricData.getString("IP"));

                            parameter.add("SSH");

                            batchAddParam.add(parameter);
                          }
                        }
                      }
                    }
                    updateStatus(batchUpdateParam);

                    addPolling(batchAddParam);

                    JsonArray sendIP = sendIps.get(1);

                    JsonArray failUpdateParam = new JsonArray();

                    JsonArray failAddParam = new JsonArray();

                    for (int j = 0; j < sendIP.size(); j++)
                    {
                      if(!successIp.contains(sendIP.getString(j)))
                      {
                        JsonArray fail = new JsonArray();
                        fail.add("failed");
                        fail.add(sendIP.getString(j));
                        fail.add("SSH");
                        failUpdateParam.add(fail);

                        JsonArray failCPU = new JsonArray();
                        failCPU.add("CPU");
                        failCPU.add("0");
                        failCPU.add(sendIP.getString(j));
                        failCPU.add("SSH");
                        failAddParam.add(failCPU);

                        JsonArray failMemory = new JsonArray();
                        failMemory.add("Memory");
                        failMemory.add("0");
                        failMemory.add(sendIP.getString(j));
                        failMemory.add("SSH");
                        failAddParam.add(failMemory);

                        JsonArray failDisk = new JsonArray();
                        failDisk.add("Disk");
                        failDisk.add("0");
                        failDisk.add(sendIP.getString(j));
                        failDisk.add("SSH");
                        failAddParam.add(failDisk);

                        JsonArray failUptime = new JsonArray();
                        failUptime.add("Uptime");
                        failUptime.add("0");
                        failUptime.add(sendIP.getString(j));
                        failUptime.add("SSH");
                        failAddParam.add(failUptime);

                        JsonArray failBpsValue = new JsonArray();
                        failBpsValue.add("BpsValue");
                        failBpsValue.add("");
                        failBpsValue.add(sendIP.getString(j));
                        failBpsValue.add("SSH");
                        failAddParam.add(failBpsValue);

                        JsonArray failLoss = new JsonArray();
                        failLoss.add("Loss");
                        failLoss.add("100%");
                        failLoss.add(sendIP.getString(j));
                        failLoss.add("SSH");
                        failAddParam.add(failLoss);

                        JsonArray failMin = new JsonArray();
                        failMin.add("Min");
                        failMin.add("0");
                        failMin.add(sendIP.getString(j));
                        failMin.add("SSH");
                        failAddParam.add(failMin);

                        JsonArray failAvg = new JsonArray();
                        failAvg.add("Avg");
                        failAvg.add("0");
                        failAvg.add(sendIP.getString(j));
                        failAvg.add("SSH");
                        failAddParam.add(failAvg);

                        JsonArray failMax = new JsonArray();
                        failMax.add("Max");
                        failMax.add("0");
                        failMax.add(sendIP.getString(j));
                        failMax.add("SSH");
                        failAddParam.add(failMax);

                        JsonArray failStatus = new JsonArray();
                        failStatus.add("Status");
                        failStatus.add("failed");
                        failStatus.add(sendIP.getString(j));
                        failStatus.add("SSH");
                        failAddParam.add(failStatus);
                      }
                    }

                    updateStatus(failUpdateParam);

                    addPolling(failAddParam);
                  }
                  else
                  {
                    JsonArray sendIP = sendIps.get(1);

                    JsonArray failUpdateParam = new JsonArray();

                    JsonArray failAddParam = new JsonArray();

                    for (int j = 0; j < sendIP.size(); j++)
                    {
                      JsonArray fail = new JsonArray();
                      fail.add("failed");
                      fail.add(sendIP.getString(j));
                      fail.add("SSH");
                      failUpdateParam.add(fail);

                      JsonArray failCPU = new JsonArray();
                      failCPU.add("CPU");
                      failCPU.add("0");
                      failCPU.add(sendIP.getString(j));
                      failCPU.add("SSH");
                      failAddParam.add(failCPU);

                      JsonArray failMemory = new JsonArray();
                      failMemory.add("Memory");
                      failMemory.add("0");
                      failMemory.add(sendIP.getString(j));
                      failMemory.add("SSH");
                      failAddParam.add(failMemory);

                      JsonArray failDisk = new JsonArray();
                      failDisk.add("Disk");
                      failDisk.add("0");
                      failDisk.add(sendIP.getString(j));
                      failDisk.add("SSH");
                      failAddParam.add(failDisk);

                      JsonArray failUptime = new JsonArray();
                      failUptime.add("Uptime");
                      failUptime.add("0");
                      failUptime.add(sendIP.getString(j));
                      failUptime.add("SSH");
                      failAddParam.add(failUptime);

                      JsonArray failBpsValue = new JsonArray();
                      failBpsValue.add("BpsValue");
                      failBpsValue.add("");
                      failBpsValue.add(sendIP.getString(j));
                      failBpsValue.add("SSH");
                      failAddParam.add(failBpsValue);

                      JsonArray failLoss = new JsonArray();
                      failLoss.add("Loss");
                      failLoss.add("100%");
                      failLoss.add(sendIP.getString(j));
                      failLoss.add("SSH");
                      failAddParam.add(failLoss);

                      JsonArray failMin = new JsonArray();
                      failMin.add("Min");
                      failMin.add("0");
                      failMin.add(sendIP.getString(j));
                      failMin.add("SSH");
                      failAddParam.add(failMin);

                      JsonArray failAvg = new JsonArray();
                      failAvg.add("Avg");
                      failAvg.add("0");
                      failAvg.add(sendIP.getString(j));
                      failAvg.add("SSH");
                      failAddParam.add(failAvg);

                      JsonArray failMax = new JsonArray();
                      failMax.add("Max");
                      failMax.add("0");
                      failMax.add(sendIP.getString(j));
                      failMax.add("SSH");
                      failAddParam.add(failMax);

                      JsonArray failStatus = new JsonArray();
                      failStatus.add("Status");
                      failStatus.add("failed");
                      failStatus.add(sendIP.getString(j));
                      failStatus.add("SSH");
                      failAddParam.add(failStatus);
                    }

                    updateStatus(failUpdateParam);

                    addPolling(failAddParam);
                  }
                }
                else
                {
                  System.err.println("Process execution failed: " + exeResult.cause().getMessage());

                  startPromise.fail(exeResult.cause());
                }
              });

            }
            else
            {
              LOGGER.error("Error: " + getResult.cause());
            }
          });
        }
        else
        {
          LOGGER.error("Error: GetMonitor is null");
        }
      });

      startPromise.complete();
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());

      startPromise.fail(exception);
    }
  }

  private void executeCommand(String command, String input, Handler<AsyncResult<String>> handler)
  {
    try
    {
      CompletableFuture<String> processOutputFuture = new CompletableFuture<>();

      command += " " + input;

      NuProcessBuilder nuProcessBuilder = new NuProcessBuilder(Arrays.asList(command.split("\\s+")));

      nuProcessBuilder.setProcessListener(new NuProcessHandler(processOutputFuture));

      vertx.executeBlocking(future ->
      {
        try
        {
          NuProcess process = nuProcessBuilder.start();

          process.waitFor(120, TimeUnit.SECONDS);
        }

        catch (Exception e)
        {
          future.fail(e);

          return;
        }

        String output = processOutputFuture.join();

        String[] outputLines = output.split("\\r?\\n");

        StringBuilder successOutput = new StringBuilder();

        for (String line : outputLines)
        {
          // Filter out error lines based on a specific pattern
          if (!line.contains("Error"))
          {
            successOutput.append(line).append(System.lineSeparator());
          }

          else
          {
            LOGGER.error(line);
          }
        }

        future.complete(successOutput.toString());

      },false, handler);
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());
    }
  }
}
