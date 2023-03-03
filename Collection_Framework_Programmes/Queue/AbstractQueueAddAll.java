package Queue;

import java.util.AbstractQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AbstractQueueAddAll {

    public static void main(String[] argv)
            throws Exception
    {
        try
        {
            AbstractQueue<Integer> AQ1 = new LinkedBlockingQueue<Integer>(5);

            AQ1.add(10);

            AQ1.add(20);

            AQ1.add(30);

            AQ1.add(40);

            AQ1.add(50);

            System.out.println("AbstractQueue1 contains : " + AQ1);

            AbstractQueue<Integer> AQ2 = new LinkedBlockingQueue<Integer>();

            System.out.println("AbstractQueue2 initially contains : " + AQ2);

            AQ2.addAll(AQ1);

            System.out.println("AbstractQueue1 after addition contains : " + AQ1);
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }
    }
}
