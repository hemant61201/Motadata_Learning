package LinkedList;

import java.util.LinkedList;

public class LinkedListPushAndPop {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<String> stack = new LinkedList<>();

            stack.push("Hello");

            stack.push("Hi");

            String s = stack.pop();

            System.out.println(s);

            stack.push("Hemant");

            System.out.println(stack);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
