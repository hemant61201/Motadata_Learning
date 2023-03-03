package Queue;

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueDemo {
    public static void main(String[] args) throws InterruptedException
    {
        try
        {
            LinkedTransferQueue<Integer> LTQ = new LinkedTransferQueue<Integer>();

            LTQ.add(7855642);

            LTQ.add(35658786);

            LTQ.add(5278367);

            LTQ.add(74381793);

            System.out.println("Linked Transfer Queue1: " + LTQ);

            LinkedTransferQueue<Integer> LTQ2 = new LinkedTransferQueue<Integer>(LTQ);

            // print Queue
            System.out.println("Linked Transfer Queue2: " + LTQ2);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}

