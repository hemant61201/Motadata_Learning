package LinkedList;

import java.util.LinkedList;

public class LinkedListLastIndexOf {

    public static void main(String args[]) {
        try {
            LinkedList<String> list = new LinkedList<String>();

            list.add("Geeks");

            list.add("for");

            list.add("Geeks");

            list.add("10");

            list.add("20");

            System.out.println("LinkedList:" + list);

            System.out.println("Last occurrence of Geeks is at index: " + list.lastIndexOf("Geeks"));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
