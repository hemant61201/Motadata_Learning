package LinkedList;

import java.util.LinkedList;

public class LinkedListAddAll {

    public static void main(String args[])
    {
        LinkedList<String> list = new LinkedList<String>();

        list.add("Hi");

        list.add("Hello");

        list.add(null);

        list.add("20");

        LinkedList<String> list1 = new LinkedList<String>();

        list1.add("1");

        list1.add("2");

        list1.add("3");

        System.out.println("The LinkedList is: " + list);

        list.addAll(list1);

        list.addAll(3, list1);

        System.out.println("The new linked list is: " + list);

    }
}
