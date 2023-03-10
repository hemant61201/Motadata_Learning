package org.example;

class Run implements Runnable
{
    public void run()
    {
        try
        {
            Thread.sleep(100);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        System.out.println("The state of thread t1 while it invoked the method join() on thread t2 -"+ ThreadState.thread1.getState());

        try
        {
            Thread.sleep(200);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}

public class ThreadState implements Runnable
{
    public static Thread thread1;

    public static ThreadState threadState;

    // main method
    public static void main(String[] args)
    {
        threadState = new ThreadState();

        thread1 = new Thread(threadState);

        System.out.println("The state of thread t1 after spawning it - " + thread1.getState());

        thread1.start();

        System.out.println("The state of thread t1 after invoking the method start() on it - " + thread1.getState());
    }

    public void run()
    {
        Run run = new Run();

        Thread thread2 = new Thread(run);

        System.out.println("The state of thread t2 after spawning it - "+ thread2.getState());

        thread2.start();

        System.out.println("the state of thread t2 after calling the method start() on it - " + thread2.getState());

        try
        {
            Thread.sleep(200);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        System.out.println("The state of thread t2 after invoking the method sleep() on it - "+ thread2.getState() );

        try
        {
            thread2.join();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        System.out.println("The state of thread t2 when it has completed it's execution - " + thread2.getState());

        System.out.println("The state of thread t1 when it has completed it's execution - " + thread1.getState());
    }

}
