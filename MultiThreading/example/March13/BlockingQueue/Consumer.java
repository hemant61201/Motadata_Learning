package org.example.March13.BlockingQueue;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    BlockingQueue<String> blockingQueue = null;

    public Consumer(BlockingQueue<String> queue)
    {
        this.blockingQueue = queue;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                String element = this.blockingQueue.take();

                String text = Thread.currentThread().getName() + " consumed " + element;

                System.out.println(text);
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
