package example;

import java.util.Iterator;
import java.util.LinkedList;

public class ReversedLinkedList {
    public static void main(String[] args)
    {

        try
        {
            LinkedList<String> list = new LinkedList<>();

            list.add("Hi");

            list.add("Hello");

            list.add("Hemant");

            Iterator<String> itr = list.descendingIterator();

            while (itr.hasNext())
            {
                System.out.println(itr.next());
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }


    }
}