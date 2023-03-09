package org.example;

public class Test extends Thread
{
    public Test(String name)
    {
        super(name);
    }

    @Override
    public void run()
    {
        try
        {
            for(int index=0; index < 5; index++)
           {
                Thread.sleep(3L * 1000L);

                System.out.println("Obj name : "+getName());

                System.out.println("Thread name : "+currentThread().getName());
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
