package Queue;

import java.util.Deque;
import java.util.LinkedList;

public class DequeContains {

    public static void main(String args[])
    {
        try
        {
            Deque<Integer> de_que = new LinkedList<Integer>();

            de_que.add(10);

            de_que.add(15);

            de_que.add(30);

            de_que.add(20);

            de_que.add(5);

            System.out.println("Deque: " + de_que);

            System.out.println("Does the Deque contains '15'? " + de_que.contains(15));

            System.out.println("Does the Deque contains '2'? " + de_que.contains(2));

            System.out.println("Does the Deque contains '10'? " + de_que.contains(10));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
