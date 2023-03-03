package Map;

import java.util.HashMap;
import java.util.Map;

public class MapEntrySet {

    public static void main(String[] args)
    {
        try
        {
            Map<String, Integer> map = new HashMap<String, Integer>();

            map.put("Geeks", 10);

            map.put("4", 15);

            map.put("Geeks", 20);

            map.put("Welcomes", 25);

            map.put("You", 30);

            System.out.println("Initial Mappings are: " + map);

            System.out.println("The set is: " + map.entrySet());
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
