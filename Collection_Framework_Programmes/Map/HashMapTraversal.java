package Map;

import java.util.*;

public class HashMapTraversal {
    public static void main(String[] args)
    {
        try
        {
            HashMap<String, Integer> hm = new HashMap<String, Integer>();

            hm.put("GeeksforGeeks", 54);

            hm.put("A computer portal", 80);

            hm.put("For geeks", 82);

            System.out.println("Created hashmap is" + hm);

            Iterator hmIterator = hm.entrySet().iterator();

            System.out.println("HashMap after adding bonus marks:");

            while (hmIterator.hasNext())
            {
                Map.Entry mapElement = (Map.Entry) hmIterator.next();

                int marks = ((int) mapElement.getValue() + 10);

                System.out.println(mapElement.getKey() + " : " + marks);
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
