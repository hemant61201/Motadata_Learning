package LinkedList;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListDescendingIterator {

    public static void main(String[] argv) throws Exception
    {
        try
        {
            LinkedList<String> list = new LinkedList<String>();

            list.add("A");

            list.add("B");

            list.add("C");

            System.out.println("LinkedList:" + list);

            Iterator x = list.descendingIterator();

            while (x.hasNext())
            {
                System.out.println("Value is : " + x.next());
            }
        }

        catch (NullPointerException e)
        {
            System.out.println("Exception thrown : " + e);
        }
    }
}
