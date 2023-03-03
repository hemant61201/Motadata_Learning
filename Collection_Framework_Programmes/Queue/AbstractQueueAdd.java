package Queue;

import java.util.AbstractQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AbstractQueueAdd {

    public static void main(String[] argv)
            throws Exception
    {
        try
        {
            AbstractQueue<Integer> AQ = new LinkedBlockingQueue<Integer>();

            AQ.add(10);

            AQ.add(20);

            AQ.add(30);

            AQ.add(null);

            AQ.add(50);

            System.out.println("AbstractQueue contains : " + AQ);
        }
        catch (Exception e)
        {
            System.out.println("exception: " + e);
        }
    }
}
