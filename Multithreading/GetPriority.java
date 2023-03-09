package org.example;

public class GetPriority extends Thread {

    @Override
    public void run()
    {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args)
    {
        try
        {
            GetPriority getPriority = new GetPriority();

            GetPriority getPriority1 = new GetPriority();

            System.out.println(getPriority.getPriority());

            System.out.println(getPriority1.getPriority());

            getPriority.start();

            getPriority1.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
