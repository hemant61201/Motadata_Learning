package org.example;

public class DaemonThread extends Thread{

    public void run()
    {
        System.out.println("Name: "+Thread.currentThread().getName());

        System.out.println("Daemon: "+Thread.currentThread().isDaemon());
    }

    public static void main(String[] args)
    {
        try
        {
            DaemonThread daemonThread1 = new DaemonThread();

            DaemonThread daemonThread2 = new DaemonThread();

            daemonThread1.start();

            daemonThread1.setDaemon(true);

            daemonThread2.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
