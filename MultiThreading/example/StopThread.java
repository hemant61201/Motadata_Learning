package org.example;

public class StopThread implements Runnable{

    private boolean doStop = false;

    public synchronized void doStop()
    {
        this.doStop = true;
    }

    public synchronized boolean keepRunning()
    {
        return !this.doStop;
    }

    @Override
    public void run()
    {
        try
        {
            while (keepRunning())
            {
                System.out.println("Running");

                Thread.sleep(3L * 1000L);
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
