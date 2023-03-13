package org.example.March13;

class Msg extends Thread
    {
        public void run()
        {
            System.out.println("Bye ...");
        }
    }

public class RemoveShutdownHookExample
{
    public static void main(String[] args)
    {
        try
        {
            Msg msg = new Msg();

            Runtime.getRuntime().addShutdownHook(msg);

            System.out.println("The program is beginning ...");

            System.out.println("Waiting for 2 seconds ...");

            Thread.sleep(2000);

            Runtime.getRuntime().removeShutdownHook(msg);

            System.out.println("The program is terminating ...");
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
