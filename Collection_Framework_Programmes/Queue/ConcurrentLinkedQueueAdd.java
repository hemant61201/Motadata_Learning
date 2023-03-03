package Queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueAdd {

    public static void main(String[] args)
    {
        try
        {
            ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();

            queue.add(4353);

            queue.add(7824);

            queue.add(78249);

            queue.add(8724);

            System.out.println("ConcurrentLinkedQueue: " + queue);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
