package org.example;

public class MyThreadWithAnonymousClass {

    public static void main(String[] args)
    {
        try
        {
            Thread thread = new Thread(() -> System.out.println("Running"));

            thread.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
