package org.example;

public class JoinMethod extends Thread {

    @Override
    public void run()
    {
        try
        {
            for(int index=0; index < 5; index++)
            {
                Thread.sleep(3 * 1000);

                System.out.println(index);
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
            JoinMethod join = new JoinMethod();

            Thread thread1 = new Thread(join);

            Thread thread2 = new Thread(join);

            Thread thread3 = new Thread(join);

            thread1.start();

            thread1.join(4000);

            thread2.start();

            thread3.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
