package LinkedList;

import java.util.LinkedList;

public class LinkedListSet {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<String> list = new LinkedList<>();

            list.add("Hi");

            list.add("Hello");

            list.add("Hemant");

            System.out.println("The initial Linked list is : " + list.set(1, "Yoo"));

            System.out.println("The initial Linked list is : " + list.poll());

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
