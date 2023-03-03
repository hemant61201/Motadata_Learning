package Queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingQueueAdd {

    public static void main(String[] args) throws IllegalStateException
    {
        try
        {
            BlockingQueue<Integer> BQ = new LinkedBlockingDeque<Integer>();

            BQ.add(7855642);

            BQ.add(35658786);

            BQ.add(5278367);

            BQ.add(74381793);

            System.out.println("Blocking Queue: " + BQ);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
