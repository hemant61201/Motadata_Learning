package org.example;

public class RunnableAnonumousClass {

    public static void main(String[] args)
    {
        try
        {
            Runnable runnable = () -> System.out.println("Running");

            Thread thread = new Thread(runnable);

            thread.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
