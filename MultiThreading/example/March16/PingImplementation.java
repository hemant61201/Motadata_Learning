package org.example.March16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class PingImplementation implements Callable<ArrayList<String>>
{
    String ip;

    PingImplementation(String ip)
    {
        this.ip = ip;
    }

    public ArrayList<String> call()
    {
        try
        {
            Process p = Runtime.getRuntime().exec("ping -c 5 " + ip);

            try(BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream())))
            {
                String pingOutput;

                int count = 0;

                ArrayList<String> pingResult = new ArrayList<>();

                boolean flag = false;

                while ((pingOutput = inputStream.readLine()) != null)
                {
                    if (pingOutput.equals("--- " + ip + " ping statistics ---"))
                    {
                        flag = true;
                    }

                    if (flag)
                    {
                        if (count > 0)
                        {
                            pingResult.add(pingOutput);
                        }
                        count++;
                    }
                }

                return pingResult;
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;
    }
}
