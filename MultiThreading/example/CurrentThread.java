package org.example;

public class CurrentThread {

    public static void main(String[] args)
    {
        try
        {
            Thread thread = Thread.currentThread();

            String threadName = thread.getName();

            System.out.println(threadName);

            System.out.println(Thread.currentThread().getName());
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
