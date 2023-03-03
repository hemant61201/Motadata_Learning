package LinkedList;

import java.util.LinkedList;

public class LinkedListPeek {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<String> list = new LinkedList<>();

            list.add("Hi");

            list.add("Hello");

            list.add("Hemant");

            System.out.println("The initial Linked list is : " + list.peek());

            System.out.println("The initial Linked list is : " + list.peekFirst());

            System.out.println("The initial Linked list is : " + list.peekLast());

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
