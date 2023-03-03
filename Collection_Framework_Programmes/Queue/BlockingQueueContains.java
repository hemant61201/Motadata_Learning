package Queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueContains {

    public static void main(String[] args) throws IllegalStateException
    {
        try
        {
            BlockingQueue<Integer> BQ = new LinkedBlockingQueue<Integer>();

            BQ.add(10);

            BQ.add(20);

            BQ.add(30);

            BQ.add(40);

            System.out.println("Blocking Queue: " + BQ.contains(50));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
