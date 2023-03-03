package Stack;

import java.util.Stack;

public class StackEmpty {

    public static void main(String[] args)
    {
        try
        {
            Stack<Integer> stk= new Stack<>();

//            stk.push(null);

            boolean result = stk.empty();

            System.out.println("Is the stack empty? " + result);

            stk.push(78);

            stk.push(113);

            stk.push(90);

            stk.push(120);

            System.out.println("Elements in Stack: " + stk);

            result = stk.empty();

            System.out.println("Is the stack empty? " + result);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
