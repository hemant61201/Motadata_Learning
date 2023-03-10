package org.example;

public class MyThread extends Thread {

    public void run()
    {
        try
        {
            System.out.println("MyThread running");
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    public static void main(String[] args)
    {
        try
        {
            MyThread myThread = new MyThread();

            myThread.start();

            myThread.run();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
