package Map;

import java.util.HashMap;
import java.util.Map;

public class MapEquals {

    public static void main(String[] args)
    {
        try
        {
            Map<Integer, String> map1 = new HashMap<Integer, String>();

            Map<Integer, String> map2 = new HashMap<Integer, String>();

            map1.put(10, "Geeks");

            map1.put(15, "4");

            map1.put(20, "Geeks");

            map1.put(null, "1");

            map1.put(25, "Welcomes");

            map2.put(10, "Geeks");

            map2.put(15, "4");

            map2.put(20, "Geeks");

            map2.put(25, "Welcomes");

            map2.put(null, "1");

            System.out.println("First Map: " + map1);

            System.out.println("Second Map: " + map2);

            System.out.println("Equality: " + map1.equals(map2));

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
