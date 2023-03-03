package Queue;

import java.util.AbstractQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AbstractQueueElement {

    public static void main(String[] argv) throws Exception
    {
        try
        {
            AbstractQueue<Integer> AQ1 = new LinkedBlockingQueue<Integer>();

            AQ1.add(10);

            AQ1.add(20);

            AQ1.add(30);

            AQ1.add(40);

            AQ1.add(50);

            System.out.println("AbstractQueue1 contains : " + AQ1);

            System.out.println("head : " + AQ1.element());
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
