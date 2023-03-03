package LinkedList;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListAddLast {

    public static void main(String args[])
    {
        LinkedList<String> linkedlist=new LinkedList<String>();

        linkedlist.add("Ravi");

        linkedlist.add("Vijay");

        linkedlist.add("Ravi");

        linkedlist.add("Ajay");

        linkedlist.add(4,"Hemant");

        linkedlist.addLast("At");

        linkedlist.addLast("Last");

        Iterator<String> itr=linkedlist.iterator();

        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }
    }
}
