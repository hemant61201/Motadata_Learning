package Stack;

import java.util.Stack;

public class StackSearch {

    public static void main(String[] args)
    {
        try
        {
            Stack<String> stk = new Stack<>();

            stk.push("Mac Book");

            stk.push("HP");

            stk.push("DELL");

            stk.push("Asus");

            System.out.println("Stack: " + stk);

            int location = stk.search("HP");

            System.out.println("Location of Dell: " + location);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
