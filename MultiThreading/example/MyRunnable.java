package org.example;

public class MyRunnable implements Runnable{

    @Override
    public void run()
    {
        System.out.println("Running");
    }

    public static void main(String[] args)
    {
        try
        {
            MyRunnable runnable = new MyRunnable();

            Thread thread = new Thread(runnable);

            thread.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
