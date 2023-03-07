package example;

import java.util.LinkedHashMap;
import java.util.Map;

public class FirstUniqeCharUsingLinkedHashMap {

    public static void main(String[] args) {

        try
        {
            String input = "loveleetcode";

            char[] chars = input.toCharArray();

            LinkedHashMap<Character, Integer> linkedhasmap = new LinkedHashMap<>();

            int value = 1;

            for (char character : chars)
            {
                if (!linkedhasmap.containsKey(character))
                {
                    linkedhasmap.put(character, value);
                }

                else
                {
                    linkedhasmap.put(character,value + 1);
                }
            }

            System.out.println(linkedhasmap);

            for (Map.Entry<Character, Integer> ite : linkedhasmap.entrySet())
            {
                if(ite.getValue() == 1)
                {
                    System.out.print(ite.getKey());

                    break;
                }
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }


    }
}
