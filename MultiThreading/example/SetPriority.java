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

            SetPriority setPriority5 = new SetPriority();

            setPriority5.setName("setPriority5");

            SetPriority setPriority6 = new SetPriority();

            setPriority6.setName("setPriority6");

            SetPriority setPriority7 = new SetPriority();

            setPriority7.setName("setPriority7");

            SetPriority setPriority8 = new SetPriority();

            setPriority8.setName("setPriority8");

            setPriority1.setPriority(Thread.MIN_PRIORITY);

            setPriority2.setPriority(Thread.MAX_PRIORITY);

            setPriority3.setPriority(7);

            setPriority4.setPriority(8);

            setPriority5.setPriority(9);

            setPriority6.setPriority(2);

            setPriority7.setPriority(3);

            setPriority8.setPriority(4);

            setPriority1.start();

            setPriority2.start();

            setPriority3.start();

            setPriority4.start();

            setPriority5.start();

            setPriority6.start();

            setPriority7.start();

            setPriority8.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
