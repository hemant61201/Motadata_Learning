package org.example;

public class SuspendExample extends Thread {

    @Override
    public void run()
    {
        try
        {
            for (int index=0; index < 3; index++)
            {
                Thread.sleep(500);

                System.out.println(Thread.currentThread().getName() + " " + index);
            }
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
            SuspendExample suspendExample1 = new SuspendExample();

            SuspendExample suspendExample2 = new SuspendExample();

            SuspendExample suspendExample3 = new SuspendExample();

            suspendExample1.start();

            suspendExample2.start();

            suspendExample2.suspend();

            suspendExample3.start();

            Thread.sleep(10L * 1000L);

            suspendExample2.resume();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
