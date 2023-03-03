package Queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueOffer {

    public static void main(String[] args) throws InterruptedException
    {
        try
        {
            int capacityOfQueue = 4;

            BlockingQueue<Integer> BQ = new LinkedBlockingQueue<Integer>(capacityOfQueue);

            System.out.println("adding 32673821 " + BQ.offer(32673821, 5, TimeUnit.SECONDS));

            System.out.println("adding 88527183: " + BQ.offer(88527183, 5, TimeUnit.SECONDS));

            System.out.println("adding 431278539: " + BQ.offer(431278539, 5, TimeUnit.SECONDS));

            System.out.println("adding 351278693: " + BQ.offer(351278693, 5, TimeUnit.SECONDS));

            System.out.println("adding 647264: " + BQ.offer(647264, 5, TimeUnit.SECONDS));

            System.out.println("list of numbers of queue:" + BQ);

            System.out.println("Empty spaces of queue : " + BQ.remainingCapacity());

            boolean response = BQ.offer(2893476, 5, TimeUnit.SECONDS);

            System.out.println("Adding new Integer 2893476 is successful: " + response);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
