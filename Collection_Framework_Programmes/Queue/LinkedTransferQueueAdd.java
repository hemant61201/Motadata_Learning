package Queue;

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueAdd {
    public static void main(String[] args)
    {
        try
        {
            LinkedTransferQueue<String> queue = new LinkedTransferQueue<String>();

            queue.add("a");

            queue.add("b");

            queue.add("c");

            queue.add("d");

            queue.add("e");

            System.out.println("The elements in the queue are:");

            for (String i : queue)
                System.out.print(i + " ");

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
