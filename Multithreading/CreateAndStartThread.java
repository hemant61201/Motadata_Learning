package org.example;

public class CreateAndStartThread
{
    public static void main(String[] args)
    {
        try
        {
            MyThread thread = new MyThread();

            thread.start();

            Thread.sleep(10 * 1000);

            thread.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}