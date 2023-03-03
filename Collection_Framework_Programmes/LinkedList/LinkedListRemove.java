package LinkedList;

import java.util.LinkedList;

public class LinkedListRemove {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<String> list = new LinkedList<>();

            list.add("Hi");

            list.add("Hello");

            list.add("Hemant");

            System.out.println("The initial Linked list is : " + list.remove());

            System.out.println("The initial Linked list is : " + list.remove(1));

            System.out.println("The initial Linked list is : " + list.remove("Hemant"));

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
