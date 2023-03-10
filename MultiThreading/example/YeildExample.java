package org.example;

public class YeildExample extends Thread
{
    public void run()
    {
        for (int i=0; i<3 ; i++)
            System.out.println(Thread.currentThread().getName() + " in control");
    }

    public static void main(String[]args)
    {
        try
        {
            YeildExample yeildExample = new YeildExample();

            YeildExample yeildExample1 = new YeildExample();

            yeildExample.start();

            yeildExample1.start();

            for (int i=0; i<3; i++)
            {
                yeildExample.yield();

                System.out.println(Thread.currentThread().getName() + " in control");
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
