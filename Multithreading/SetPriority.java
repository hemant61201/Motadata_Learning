package org.example;

public class SetPriority extends Thread{

    @Override
    public void run()
    {
        System.out.println("Running : " + Thread.currentThread().getName());
    }

    public static void main(String[] args)
    {
        try
        {
            SetPriority setPriority1 = new SetPriority();

            setPriority1.setName("setPriority1");

            SetPriority setPriority2 = new SetPriority();

            setPriority2.setName("setPriority2");

            SetPriority setPriority3 = new SetPriority();

            setPriority3.setName("setPriority3");

            SetPriority setPriority4 = new SetPriority();

            setPriority4.setName("setPriority4");

            setPriority1.setPriority(Thread.MIN_PRIORITY);

            setPriority2.setPriority(Thread.MAX_PRIORITY);

            setPriority3.setPriority(7);

            setPriority1.start();

            setPriority2.start();

            setPriority3.start();

            setPriority4.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
