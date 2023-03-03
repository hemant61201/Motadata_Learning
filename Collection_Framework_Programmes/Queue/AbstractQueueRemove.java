package Queue;

import java.util.AbstractQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AbstractQueueRemove {

    public static void main(String[] argv) throws Exception
    {
        try
        {
            AbstractQueue<Integer> AQ1 = new LinkedBlockingQueue<Integer>();

            AQ1.add(10);

            System.out.println("AbstractQueue1 contains : " + AQ1);

            int head = AQ1.remove();

            System.out.println("head : " + head);

            head = AQ1.remove();

            System.out.println("head : " + head);
        }

        catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }
    }
}
