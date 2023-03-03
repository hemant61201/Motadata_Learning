package Set;

import java.util.*;

public class TreeSetExample {

    // Main driver method
    public static void main(String[] args)
    {
        try
        {
            NavigableSet<String> ts = new TreeSet<String>();

            ts.add("A");

            ts.add("B");

            ts.add("Z");

            System.out.println("Initial TreeSet " + ts);

            ts.remove("B");

            System.out.println("After removing element " + ts);

            ts.pollFirst();

            System.out.println("After removing first " + ts);

            ts.pollLast();

            System.out.println("After removing last " + ts);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
