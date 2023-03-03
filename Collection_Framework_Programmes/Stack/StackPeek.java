package Stack;

import java.util.Stack;

public class StackPeek {

    public static void main(String[] args)
    {
        try
        {
            Stack<String> stk= new Stack<>();

            stk.push("Apple");

            stk.push("Grapes");

            stk.push("Mango");

            stk.push("Orange");

            System.out.println("Stack: " + stk);

            String fruits = stk.peek();

            System.out.println("Element at top: " + fruits);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
