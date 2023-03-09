package org.example;

public class ThreadName {

    public static void main(String[] args)
    {
        try
        {
            Thread thread = new Thread("New Thread")
            {
                @Override
                public void run()
                {
                    System.out.println("Name : " + currentThread().getName());
                }
            };

            thread.start();

            System.out.println(thread.getName());
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
