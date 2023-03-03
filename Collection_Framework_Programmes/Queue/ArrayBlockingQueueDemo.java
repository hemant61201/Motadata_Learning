package Queue;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueDemo {

    public static void main(String[] args)
    {
        try
        {
            int capacity = 15;

            ArrayList<Integer> ar = new ArrayList<>();

            ar.add(1);

            ar.add(2);

            ar.add(3);

            ar.add(4);

            ArrayBlockingQueue<Integer> abq = new ArrayBlockingQueue<Integer>(capacity, true, ar);

            abq.add(1);

            abq.add(2);

            abq.add(3);

            System.out.println("ArrayBlockingQueue:" + abq);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}

