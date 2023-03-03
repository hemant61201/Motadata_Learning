package Stack;

import java.util.Stack;

public class StackPushPop {

    public static void main(String args[])
    {
        try
        {
            Stack<Integer> stack = new Stack<Integer>();

            stack.push(10);

            stack.push(15);

            stack.push(30);

            stack.push(20);

            stack.push(5);

            System.out.println("Initial Stack: " + stack);

            System.out.println("Popped element: " + stack.pop());

            System.out.println("Popped element: " + stack.pop());

            System.out.println("Stack after pop operation " + stack);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
