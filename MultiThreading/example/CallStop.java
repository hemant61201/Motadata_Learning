package org.example;

public class CallStop {

    public static void main(String[] args)
    {
        try
        {
            StopThread stopThread = new StopThread();

            Thread thread = new Thread(stopThread);

            thread.start();

            Thread.sleep(10L * 1000L);

            stopThread.doStop();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
