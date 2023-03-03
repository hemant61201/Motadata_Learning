package Queue;

import java.util.AbstractQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AbstractQueueClear {

    public static void main(String[] argv) throws Exception
    {
        try
        {
            AbstractQueue<String> AQ1 = new LinkedBlockingQueue<String>();

            AQ1.add("gopal");

            AQ1.add("gfg");

            AQ1.add("java");

            System.out.println("AbstractQueue1 contains : " + AQ1);

            AQ1.clear();

            System.out.println("AbstractQueue1 : " + AQ1);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
