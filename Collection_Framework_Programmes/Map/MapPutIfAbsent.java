package Map;

import java.util.HashMap;

public class MapPutIfAbsent {

    public static void main(String[] args)
    {
        try
        {
            HashMap<String, Integer> map = new HashMap<>();

            map.put("a", 10000);

            map.put("b", 55000);

            map.put("c", 44300);

            map.put("e", 10000);

            System.out.println("HashMap:\n " + map.toString());

            Integer r1 = map.putIfAbsent("d", 77633);

            Integer r2 = map.putIfAbsent("e", 77633);

            System.out.println("Value of r1:\n " + r1);

            System.out.println("Value of r2:\n " + r2);

            System.out.println("New HashMap:\n " + map);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
