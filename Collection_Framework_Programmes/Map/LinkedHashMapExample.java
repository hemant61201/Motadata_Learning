package Map;

import java.util.*;

public class LinkedHashMapExample {

    public static void main(String a[])
    {
        try
        {
            LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();

            lhm.put("one", "practice.org");

            lhm.put("two", "code.org");

            lhm.put("four", "www.org");

            System.out.println(lhm);

            System.out.println("Getting value for key 'one': " + lhm.get("one"));

            System.out.println("Size of the map: " + lhm.size());

            System.out.println("Is map empty? " + lhm.isEmpty());

            System.out.println("Contains key 'two'? " + lhm.containsKey("two"));

            System.out.println("Contains value 'practice.geeks" + "forgeeks.org'? " + lhm.containsValue("practice" + ".geeksforgeeks.org"));

            System.out.println("delete element 'one': " + lhm.remove("one"));

            System.out.println("Mappings of LinkedHashMap : " + lhm);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
