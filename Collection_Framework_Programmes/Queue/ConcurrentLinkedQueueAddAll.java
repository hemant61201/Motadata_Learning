package Queue;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueAddAll {

    public static void main(String[] args)
    {
        try
        {
            ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();

            queue.add("Aman");

            queue.add("Amar");

            ArrayList<String> arraylist = new ArrayList<String>();

            arraylist.add("Sanjeet");

            arraylist.add("Rabi");

            arraylist.add("Debasis");

            arraylist.add("Raunak");

            arraylist.add("Mahesh");

            System.out.println("ConcurrentLinkedQueue: " + queue);

            System.out.println("Collection to be added: " + arraylist);

            boolean response = queue.addAll(arraylist);

            System.out.println("Collection added: " + response);

            System.out.println("ConcurrentLinkedQueue: " + queue);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
