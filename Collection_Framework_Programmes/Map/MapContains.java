package Map;

import java.util.HashMap;
import java.util.Map;

public class MapContains {

    public static void main(String[] args)
    {
        try
        {
            Map<Integer, String> map = new HashMap<Integer, String>();

            map.put(10, "Geeks");

            map.put(15, "4");

            map.put(20, "Geeks");

            map.put(25, "Welcomes");

            map.put(30, "You");

            System.out.println("Initial Mappings are: " + map);

            System.out.println("Is the key '20' present? " + map.containsKey(20));

            System.out.println("Finally the maps look like this: " + map);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
