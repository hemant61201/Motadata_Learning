package example;

import java.util.HashMap;

public class NumberHasEqualDigitCountAndDigitValue {

    public static void main(String[] args)
    {

        try
        {
            String num = "1210";

            char[] chars = num.toCharArray();

            HashMap<Character, Integer> hashmap1 = new HashMap<>();

            for (char character : chars)
            {

                if(!hashmap1.containsKey(character))
                {
                    hashmap1.put(character, 1);
                }

                else
                {
                    hashmap1.put(character, hashmap1.get(character) + 1);
                }
            }

            System.out.println(hashmap1.values());

            Object[] result = hashmap1.values().toArray();

            String output = "";

            for (Object object : result)
            {
                output += object.toString();
            }

            int index = Character.getNumericValue(output.charAt(0));

            output += "0".repeat(index);

            System.out.println(output);

            System.out.println(output.equals(num));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
