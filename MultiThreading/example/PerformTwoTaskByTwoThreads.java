package org.example;

public class PerformTwoTaskByTwoThreads {


    public static void main(String[] args)
    {
        try
        {
            MyThread firstThread = new MyThread();

            MyThread1 secondThread = new MyThread1();

            secondThread.start();

            firstThread.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
