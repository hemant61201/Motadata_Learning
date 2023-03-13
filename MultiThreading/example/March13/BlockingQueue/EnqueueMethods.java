package org.example.March13.BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
public class EnqueueMethods {

    public static void main(String[] args)
    {
        BlockingQueue<String> blockingQueue  = new ArrayBlockingQueue<>(3);

        try
        {
            blockingQueue.put("1");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        try
        {
            blockingQueue.add("2");
        }
        catch(IllegalStateException e)
        {
            e.printStackTrace();
        }

        boolean wasEnqueued = blockingQueue.offer("3");

        try
        {
            boolean wasEnqueued2 = blockingQueue.offer("3", 1000, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }
}
