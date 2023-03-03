package LinkedList;

import java.util.LinkedList;

public class LinkedListSize {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<String> list = new LinkedList<>();

            list.add("Hi");

            list.add("Hello");

            list.add("Hemant");

            System.out.println("The initial Linked list is : " + list.size());

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
