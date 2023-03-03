package LinkedList;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListClear {

    public static void main(String args[])
    {
        LinkedList<String> linkedlist = new LinkedList<String>();

        linkedlist.add("Ravi");

        linkedlist.add("Vijay");

        linkedlist.add("Ravi");

        linkedlist.add("Ajay");

        Iterator<String> itr=linkedlist.iterator();

        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }

        linkedlist.clear();

        Iterator<String> itr1=linkedlist.iterator();

        while(itr1.hasNext())
        {
            System.out.println(itr1.next());
        }

    }
}
