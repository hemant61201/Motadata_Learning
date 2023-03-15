package org.example.March14;

class ThreadNotify implements Runnable
{
    public void doWait(Thread thread)
    {
        synchronized (thread)
        {
            try
            {
                System.out.println(thread.getName() + " " + thread.getState());

                thread.wait();
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public void doNotify(Thread thread)
    {
        synchronized (thread)
        {

            try
            {
                System.out.println(thread.getName() + " " + thread.getState());

                thread.notify();
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void run()
    {
        for (int index = 0; index < 5; index++)
        {
            System.out.println(Thread.currentThread().getName() + " " + index);
        }

    }
}
