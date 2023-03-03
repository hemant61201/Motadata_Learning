package LinkedList;

import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListListIterator
{
    public static void main(String args[])
    {
        try
        {
            LinkedList<String> list = new LinkedList<String>();

            list.add("Hello");

            list.add("Hi");

            list.add("Hemant");

            list.add("10");

            list.add("20");

            System.out.println("LinkedList:" + list);

            ListIterator list_Iter = list.listIterator(2);

            System.out.println("The list is as follows:");

            while(list_Iter.hasNext())
            {
                System.out.println(list_Iter.next());
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
