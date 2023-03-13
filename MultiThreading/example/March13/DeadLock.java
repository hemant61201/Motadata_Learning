package org.example.March13;

public class DeadLock {

    public static void main(String[] args)
    {
        final String resource1 = "ratan jaiswal";

        final String resource2 = "vimal jaiswal";

        Thread thread1 = new Thread()
        {
            public void run()
            {
                synchronized (resource1)
                {
                    System.out.println("Thread 1: locked resource 1");

                    try
                    {
                        Thread.sleep(100);
                    }

                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }

                    synchronized (resource2)
                    {
                        System.out.println("Thread 1: locked resource 2");
                    }
                }
            }
        };

        Thread thread2 = new Thread()
        {
            public void run()
            {
                synchronized (resource2)
                {
                    System.out.println("Thread 2: locked resource 2");

                    try
                    {
                        Thread.sleep(100);
                    }

                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }

                    synchronized (resource1)
                    {
                        System.out.println("Thread 2: locked resource 1");
                    }
                }
            }
        };

        thread1.start();

        thread2.start();
    }
}
