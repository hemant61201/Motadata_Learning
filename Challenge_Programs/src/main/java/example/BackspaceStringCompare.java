package example;

import java.util.Stack;

public class BackspaceStringCompare {

    public static void main(String[] args) {

        try
        {
            String first = "a##c";

            String second = "#a#c";

            char[] array1 = first.toCharArray();

            char[] array2 = second.toCharArray();

            Stack<Character> stack1 = new Stack<>();

            Stack<Character> stack2 = new Stack<>();

            for (char character : array1)
            {
                if (character == '#')
                {
                    if(stack1.isEmpty())
                    {
                        continue;
                    }
                    stack1.pop();
                }
                else
                {
                    stack1.push(character);
                }
            }

            for (char character : array2)
            {
                if (character == '#')
                {
                    if(stack2.isEmpty())
                    {
                        continue;
                    }

                    stack2.pop();
                }
                else
                {
                    stack2.push(character);
                }
            }

            System.out.println(stack1.equals(stack2));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }


    }
}
