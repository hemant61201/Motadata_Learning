package org.example.March13;

public class InterruptExample extends Thread{

    public void run()
    {
        try
        {
            Thread.sleep(500);

            System.out.println("javatpoint");
        }

        catch(Exception exception)
        {
            System.out.println("Exception handled "+exception);
        }

        System.out.println("thread is running...");
    }

    public static void main(String[] args)
    {
        InterruptExample interruptExample1 = new InterruptExample();

        interruptExample1.start();

        interruptExample1.interrupt();
    }
}
