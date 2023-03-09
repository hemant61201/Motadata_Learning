package org.example;

public class ThreadNameRunnable{

    public static void main(String[] args)
    {
        try
        {
            MyRunnable runnable = new MyRunnable();

            Thread thread = new Thread(runnable, "New Thread");

            thread.start();

            System.out.println(thread.getName());
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
