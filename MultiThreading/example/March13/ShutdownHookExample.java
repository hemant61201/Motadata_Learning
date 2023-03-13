package org.example.March13;

class MyThread extends Thread
{
    public void run()
    {
        System.out.println("shut down hook task completed..");
    }
}

public class ShutdownHookExample
{
    public static void main(String[] args)
    {
       try
       {
           Runtime runtime = Runtime.getRuntime();

           runtime.addShutdownHook(new MyThread());

           System.out.println("Now main sleeping... press ctrl+c to exit");

           Thread.sleep(3000);
       }

       catch (Exception exception)
       {
           exception.printStackTrace();
       }
    }
}
