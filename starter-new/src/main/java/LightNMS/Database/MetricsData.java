package LightNMS.Database;

import java.util.Map;


public class MetricsData
{
  private Map<String, Map<String, String>> max;
  private Map<String, Map<String, String>> cpu;

  private Map<String, Map<String, String>> memory;

  private Map<String, Map<String, String>> disk;
  private int success;
  private int failed;
  private int unknown;

  public void setSuccess(String success)
  {
    this.success = Integer.parseInt(success);
  }

  public void setFailed(String failed)
  {
    this.failed = Integer.parseInt(failed);
  }

  public void setUnknown(String unknown)
  {
    this.unknown = Integer.parseInt(unknown);
  }

  public Map<String, Map<String, String>> getMax()
  {
    return max;
  }

  public void setMax(Map<String, Map<String, String>> max)
  {
    this.max = max;
  }

  public Map<String, Map<String, String>> getCpu()
  {
    return cpu;
  }

  public void setCpu(Map<String, Map<String, String>> cpu)
  {
    this.cpu = cpu;
  }

  public Map<String, Map<String, String>> getMemory()
  {
    return memory;
  }

  public void setMemory(Map<String, Map<String, String>> memory)
  {
    this.memory = memory;
  }

  public Map<String, Map<String, String>> getDisk()
  {
    return disk;
  }

  public void setDisk(Map<String, Map<String, String>> disk)
  {
    this.disk = disk;
  }

  public int getSuccess()
  {
    return success;
  }

  public void setSuccess(int success)
  {
    this.success = success;
  }

  public int getFailed()
  {
    return failed;
  }

  public void setFailed(int failed)
  {
    this.failed = failed;
  }

  public int getUnknown()
  {
    return unknown;
  }

  public void setUnknown(int unknown)
  {
    this.unknown = unknown;
  }
}
