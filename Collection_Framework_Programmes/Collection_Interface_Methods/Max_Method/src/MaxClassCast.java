import java.util.*;

public class MaxClassCast {
    public static void main(String[] args) {
        try
        {
            List<String> list = new LinkedList<String>();

            Object i = Integer.valueOf(42);

            list.add("Hello");
            
            list.add((String)i);

            System.out.println("Max value is: " + Collections.max(list));
        }

        catch (ClassCastException e)
        {
            System.out.println("Exception thrown : " + e);
        }

        catch (NoSuchElementException e)
        {
            System.out.println("Exception thrown : " + e);
        }
    }
}
