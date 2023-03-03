package LinkedList;

import java.util.*;

public class LinkedListAdd
{
    public static void main(String args[])
    {
        LinkedList<String> linkedlist=new LinkedList<String>();

        linkedlist.add("Ravi");

        linkedlist.add("Vijay");

        linkedlist.add("Ravi");

        linkedlist.add("Ajay");

        linkedlist.add(4,"Hemant");

        Iterator<String> itr=linkedlist.iterator();

        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }
    }
}
