package ListDataStructure;

import java.util.ArrayList;

public class ArrayListSubList {

    public static void main(String[] argv) throws Exception
    {
        try
        {
            ArrayList<String> arrlist = new ArrayList<String>();

            arrlist.add("A");

            arrlist.add("B");

            arrlist.add("C");

            arrlist.add("D");

            arrlist.add("E");

            System.out.println("Original arrlist: " + arrlist);

            ArrayList<String> arrlist2 = (ArrayList<String>) arrlist.subList(2, 4);

            System.out.println("Sublist of arrlist: " + arrlist2);

        }

        catch (IndexOutOfBoundsException e)
        {
            System.out.println("Exception thrown : " + e);
        }

        catch (IllegalArgumentException e)
        {
            System.out.println("Exception thrown : " + e);
        }
    }
}
