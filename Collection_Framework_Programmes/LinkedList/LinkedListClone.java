package LinkedList;

import java.util.LinkedList;

public class LinkedListClone {

    public static void main(String args[])
    {
        LinkedList<String> linkedlist = new LinkedList<String>();

        linkedlist.add("Ravi");

        linkedlist.add("Vijay");

        linkedlist.add("Ravi");

        linkedlist.add("Ajay");

        System.out.println("First LinkedList:" + linkedlist);

        LinkedList sec_list = new LinkedList();

        sec_list = (LinkedList) linkedlist.clone();

        sec_list.add("Hemant");

        System.out.println("Second LinkedList is:" + linkedlist);
    }
}
