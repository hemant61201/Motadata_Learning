package Map;

import java.util.HashMap;

public class MapGetOrDefault {

    public static void main(String[] args)
    {
        try
        {
            HashMap<String, Integer> map = new HashMap<>();

            map.put("a", 100);

            map.put("b", 200);

            map.put("c", 300);

            map.put("d", 400);

            System.out.println("HashMap: " + map.toString());

            int k = map.getOrDefault("b", 500);

            System.out.println("Returned Value: " + k);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
