package LinkedList;

import java.util.LinkedList;

public class LinkedListGet {

    public static void main(String[] argv) throws Exception
    {
        try
        {
            LinkedList<String> list = new LinkedList<String>();

            list.add("A");

            list.add("B");

            System.out.println(list.get(0));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
