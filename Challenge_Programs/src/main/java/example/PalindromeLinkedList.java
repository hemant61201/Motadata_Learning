package example;

import java.util.Iterator;
import java.util.LinkedList;

public class PalindromeLinkedList {

    public static void main(String[] args) {

        try
        {
            LinkedList<String> list1 = new LinkedList<>();

            LinkedList<String> list2 = new LinkedList<>();

            LinkedList<String> list3 = new LinkedList<>();

            list1.add("Hi");

            list1.add("Hello");

            list1.add("Hemant");

            list2.add("Hemant");

            list2.add("Hello");

            list2.add("Hi");

            Iterator<String> itr = list2.descendingIterator();

            while (itr.hasNext())
            {
                list3.add(itr.next());
            }

            System.out.println(list1.equals(list3));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
