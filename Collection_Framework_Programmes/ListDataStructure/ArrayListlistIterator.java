package ListDataStructure;

import java.util.ArrayList;
import java.util.ListIterator;

public class ArrayListlistIterator {

    public static void main(String[] argv) throws Exception
    {
        try
        {
            ArrayList<String> arrlist = new ArrayList<String>();

            arrlist.add("A");

            arrlist.add("B");

            arrlist.add("C");

            arrlist.add("D");

            System.out.println("ArrayList: " + arrlist);

            ListIterator<String> iterator = arrlist.listIterator();

            System.out.println("\nUsing ListIterator:\n");

            while (iterator.hasNext())
            {
                System.out.println("Value is : " + iterator.next());
            }
        }

        catch (NullPointerException exception)
        {
            exception.printStackTrace();
        }
    }
}
