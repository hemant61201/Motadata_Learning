package example;

import java.util.ArrayDeque;
import java.util.Queue;

public class FirstUniqeChar {

    public static void main(String[] args)
    {
        try
        {
            String input = "loveleetcode";

            char[] chars = input.toCharArray();

            Queue<Character> queue1 = new ArrayDeque<>();

            for(char character : chars)
            {
                if(queue1.contains(character))
                {
                    queue1.remove(character);
                }

                queue1.add(character);
            }

            System.out.println(input.indexOf(queue1.peek()));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
