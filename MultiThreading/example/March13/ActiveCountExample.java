package org.example.March13;

public class ActiveCountExample extends Thread
{
    ActiveCountExample(String threadname, ThreadGroup tg)
    {
        super(tg, threadname);

        start();
    }

    public void run()
    {
        System.out.println("running thread name is: "+Thread.currentThread().getName());
    }

    public static void main(String[] arg)
    {
        ThreadGroup threadGroup = new ThreadGroup("parent thread group");

        ActiveCountExample activeCountExample1 = new ActiveCountExample("Thread-1", threadGroup);

        ActiveCountExample activeCountExample2 = new ActiveCountExample("Thread-2", threadGroup);

        System.out.println("number of active thread: "+ threadGroup.activeCount());
    }

}
