package org.example;

public class IsAliveExample extends Thread
{
    public void run()
    {
        try
        {
            Thread.sleep(300);

            System.out.println("is run() method isAlive "+Thread.currentThread().isAlive());
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
            IsAliveExample isAliveExample = new IsAliveExample();

            System.out.println("before starting thread isAlive: " + isAliveExample.isAlive());

            isAliveExample.start();

            System.out.println("after starting thread isAlive: " + isAliveExample.isAlive());

            Thread.sleep(100);

            System.out.println("After Sleep: " + isAliveExample.isAlive());

            Thread.sleep(1000);

            System.out.println("After 1000 Sleep: " + isAliveExample.isAlive());
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

}
