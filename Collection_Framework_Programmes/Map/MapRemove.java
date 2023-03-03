package Map;

import java.util.HashMap;
import java.util.Map;

public class MapRemove {

    public static void main(String args[])
    {
        try
        {
            Map<StringBuffer, String> hm1 = new HashMap<StringBuffer, String>();

            hm1.put(new StringBuffer("1"), "Hello");

            hm1.put(new StringBuffer("2"), "Hi");

            hm1.put(new StringBuffer("3"), "Hemant");

            hm1.put(new StringBuffer("4"), "For");

            System.out.println(hm1);

            hm1.remove(new StringBuffer("4"));

            System.out.println(hm1);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
