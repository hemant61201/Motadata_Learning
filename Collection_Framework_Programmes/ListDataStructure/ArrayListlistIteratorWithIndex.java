package ListDataStructure;

import java.util.ArrayList;
import java.util.ListIterator;

public class ArrayListlistIteratorWithIndex {

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

            ListIterator<String> iterator = arrlist.listIterator(2);

            System.out.println("\nUsing ListIterator" + " from Index 2:\n");

            while (iterator.hasNext())
            {
                System.out.println("Value is : " + iterator.next());
            }
        }

        catch (IndexOutOfBoundsException e)
        {
            System.out.println("Exception thrown : " + e);
        }
    }
}
