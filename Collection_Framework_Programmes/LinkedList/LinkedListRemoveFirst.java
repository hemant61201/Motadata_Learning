package LinkedList;

import java.util.LinkedList;

public class LinkedListRemoveFirst {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<String> list = new LinkedList<>();

            list.add("Hi");

            list.add("Hello");

            list.add("Hemant");

            System.out.println("The initial Linked list is : " + list.removeFirst());

            System.out.println("The initial Linked list is : " + list.removeFirstOccurrence("Hemant"));

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
