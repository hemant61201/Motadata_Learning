package org.example;

import java.util.Iterator;
import java.util.LinkedList;

public class ReversedLinkedList {
    public static void main(String[] args) {

        LinkedList<String> ll = new LinkedList<>();

        ll.add("Hi");

        ll.add("Hello");

        ll.add("Hemant");

        Iterator<String> itr = ll.descendingIterator();

        while (itr.hasNext())
        {
            System.out.println(itr.next());
        }


    }
}