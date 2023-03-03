package LinkedList;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListAddFirst {

    public static void main(String args[])
    {
        LinkedList<String> linkedlist=new LinkedList<String>();

        linkedlist.add("Ravi");

        linkedlist.add("Vijay");

        linkedlist.add("Ravi");

        linkedlist.add("Ajay");

        linkedlist.add(4,"Hemant");

        linkedlist.addFirst("First");

        linkedlist.addFirst("At");

        Iterator<String> itr=linkedlist.iterator();

        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }
    }
}
