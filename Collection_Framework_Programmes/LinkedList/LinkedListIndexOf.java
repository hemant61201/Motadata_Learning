package LinkedList;

import java.util.LinkedList;

public class LinkedListIndexOf {

    public static void main(String args[])
    {
        try
        {
            LinkedList<String> list = new LinkedList<String>();

            list.add("Geeks");

            list.add("for");

            list.add("Geeks");

            list.add("10");

            list.add("20");

            System.out.println("LinkedList:" + list);

            System.out.println("The first occurrence of Geeks is at index:" + list.indexOf("Geeks"));

            System.out.println("The first occurrence of 10 is at index: " + list.indexOf("10"));

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
