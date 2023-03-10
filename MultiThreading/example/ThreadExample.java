package org.example;

public class ThreadExample {

    public static void main(String[] args)
    {
        try
        {
            System.out.println(Thread.currentThread().getName());

            for (int index =0; index < 10; index++)
            {
                new Thread("" + index)
                {
                    @Override
                    public void run()
                    {
                        // executing in parallel and not sequentially

                        System.out.println("Running" + getName());
                    }
                }.start();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
