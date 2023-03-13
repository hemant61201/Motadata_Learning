package org.example.March13;

public class HoldLockExample extends Thread{

    public void run()
    {
        System.out.println("Currently executing thread is: " + Thread.currentThread().getName());

        System.out.println("Does thread holds lock? " + Thread.holdsLock(this));

        synchronized (this)
        {
            System.out.println("Does thread holds lock? " + Thread.holdsLock(this));
        }
    }

    public static void main(String[] args)
    {
        HoldLockExample g1 = new HoldLockExample();

        Thread t1 = new Thread(g1);

        t1.start();
    }
}
