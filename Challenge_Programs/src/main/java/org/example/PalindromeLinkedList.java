package org.example;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class PalindromeLinkedList {

    public static void main(String[] args) {

        try
        {
            LinkedList<String> ll = new LinkedList<>();

            LinkedList<String> ll1 = new LinkedList<>();

            LinkedList<String> ll2 = new LinkedList<>();

            ll.add("Hi");

            ll.add("Hello");

            ll.add("Hemant");

            ll1.add("Hemant");

            ll1.add("Hello");

            ll1.add("Hi");

            Iterator<String> itr = ll1.descendingIterator();

            while (itr.hasNext())
            {
                ll2.add(itr.next());
            }

            System.out.println(ll.equals(ll2));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
