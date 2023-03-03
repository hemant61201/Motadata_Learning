package Queue;

import java.util.ArrayDeque;
import java.util.Deque;

public class DequeAdd {
    public static void main(String[] args) throws IllegalStateException
    {
        try
        {
            Deque<Integer> DQ = new ArrayDeque<Integer>();

            DQ.add(35658786);

            DQ.addLast(5278367);

            DQ.add(74381793);

            DQ.addFirst(7855642);

            System.out.println("Deque: " + DQ);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
