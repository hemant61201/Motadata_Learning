package ListDataStructure;

import java.util.ArrayList;

public class ArrayListSize {

    public static void main(String[] argv) throws Exception
    {
        try
        {
            ArrayList<String> arrlist = new ArrayList<String>(null);

            arrlist.add("A");

            arrlist.add("B");

            arrlist.add("C");

            System.out.println("Before operation: " + arrlist);

            int size = arrlist.size();

            System.out.println("Size of list = " + size);
        }

        catch (IndexOutOfBoundsException e)
        {
            System.out.println("Exception thrown: " + e);
        }
    }
}
