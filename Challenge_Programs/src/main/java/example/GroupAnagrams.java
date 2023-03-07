package example;

import java.util.*;

public class GroupAnagrams {

    public static void main(String[] args)
    {

        try
        {
            String[] input = {"eat","tea","tan","ate","nat","bat","tae"};

            HashMap<String, String> hashmap1 = new HashMap<>();

            for (String string1 : input)
            {
                char[] chars = string1.toCharArray();

                Arrays.sort(chars);

                String sorted = new String(chars);

                if (hashmap1.containsKey(sorted))
                {
                    HashMap<String, String> hashmap2 = new HashMap<>();

                    hashmap2.put(sorted, string1);

                    hashmap2.forEach((key, value) -> hashmap1.merge(key, value, (v1, v2) -> v1.equalsIgnoreCase(v2) ? v1 : v1 + ", " + v2));
                }

                else
                {
                    hashmap1.put(sorted, string1);
                }
            }

            Iterator<Map.Entry<String, String>> hsiterator = hashmap1.entrySet().iterator();

            String[] array1 = new String[input.length];

            int index = 0;

            ArrayList<String> result = new ArrayList<>();

            while (hsiterator.hasNext())
            {
                Map.Entry mapElement = (Map.Entry) hsiterator.next();

                array1[index] = (String) mapElement.getValue();

                result.add("[" + array1[index] + "]");

                index++;
            }

            System.out.println(result);

            System.out.println(hashmap1);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
