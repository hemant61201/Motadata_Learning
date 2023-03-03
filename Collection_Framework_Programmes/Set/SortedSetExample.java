package Set;

import java.util.*;

public class SortedSetExample {

    public static void main(String[] args)
    {
        try
        {
            SortedSet<String> ts = new TreeSet<String>();

            ts.add("India");

            ts.add("Australia");

            ts.add("South Africa");

            ts.add("India");

            System.out.println(ts);

            ts.remove("Australia");

            System.out.println("Set after removing " + "Australia:" + ts);

            System.out.println("Iterating over set:");

            Iterator<String> i = ts.iterator();

            while (i.hasNext())
                System.out.println(i.next());

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
