package LinkedList;

import java.util.LinkedList;

public class LinkedListContains {

    public static void main(String args[])
    {
        LinkedList<StringBuffer> list = new LinkedList<StringBuffer>();

        list.add(new StringBuffer("10"));

        list.add(new StringBuffer("20"));

        list.add(null);

        LinkedList<String> list1 = new LinkedList<>();

        list1.add(null);

        System.out.println("LinkedList:" + list);

        System.out.println("\nDoes the List contains 'Hello': " + list.contains(null));

        System.out.println("Does the List contains '20': " + list.contains("20"));

        System.out.println(list1.contains(null));
    }
}
