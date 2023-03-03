package LinkedList;

import java.util.LinkedList;

public class LinkedListOffer {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<String> list = new LinkedList<>();

            list.add("Hi");

            list.add("Hello");

            list.add("Hemant");

            System.out.println("The initial Linked list is : " + list);

            list.offer(null);

            list.offerFirst("Yoo");

            list.offerLast("Bye");

            System.out.println("LinkedList after insertion using offer() : " + list);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}

