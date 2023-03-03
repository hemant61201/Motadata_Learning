package LinkedList;

import java.util.LinkedList;

public class LinkedListPoll {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<String> list = new LinkedList<>();

            list.add("Hi");

            list.add("Hello");

            list.add("Hemant");

            System.out.println("The initial Linked list is : " + list.pollFirst());

            System.out.println("The initial Linked list is : " + list.poll());

            System.out.println("The initial Linked list is : " + list.pollLast());

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
