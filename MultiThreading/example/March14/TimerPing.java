package org.example.March14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

class PingTask extends TimerTask
{

    @Override
    public void run()
    {
        try
        {
            Process p = Runtime.getRuntime().exec("ping -c 5 google.com");

            try(BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream())))
            {
                String pingoutput;

                while ((pingoutput = inputStream.readLine()) != null)
                {
                    System.out.println(pingoutput);
                }
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
    }
}
public class TimerPing
{
    public static void main(String[] args)
    {
        PingTask pingTask = new PingTask();
        pingTask.run();
        long delay = 100;
        long repeatPeriod = 60 * 1000;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(pingTask, delay, repeatPeriod);//Repeat task every 1000ms
    }
}
