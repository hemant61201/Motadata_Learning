package org.example.March13;

public class WaitAndNotify {

    WaitAndNotify myMonitorObject = new WaitAndNotify();
    boolean wasSignalled = false;
    public void doWait()
    {
        synchronized(myMonitorObject)
        {
            while(!wasSignalled)
            {
                try
                {
                    myMonitorObject.wait();
                }

                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
            }

            wasSignalled = false;
        }
    }

    public void doNotify()
    {
        synchronized(myMonitorObject)
        {
            wasSignalled = true;

            myMonitorObject.notify();
        }
    }

}
