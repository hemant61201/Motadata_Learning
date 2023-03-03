package LinkedList;

import java.util.LinkedList;

public class LinkedListGetFirst {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<String> list = new LinkedList<String>();

            list.add("Geeks");

            list.add("4");

            list.add("Geeks");

            list.add("8");

            System.out.println("The elements in List are : " + list);

            System.out.println("Element at 1st index is : " + list.getFirst());
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
