package org.example;

public class PauseThread {

    public static void main(String[] args)
    {
        try
        {
            Thread thread = new Thread(() -> System.out.println("Running"));

            Thread.sleep(10L * 1000L);

            thread.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
