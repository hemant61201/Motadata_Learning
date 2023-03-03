package ListDataStructure;

import java.util.ArrayList;

public class ArrayListRemoveAll {

    public static void main(String[] argv) throws Exception
    {
        try
        {
            ArrayList<Integer> arrlist1 = new ArrayList<Integer>();

            arrlist1.add(1);

            arrlist1.add(2);

            arrlist1.add(null);

            arrlist1.add(4);

            arrlist1.add(5);

            System.out.println("ArrayList before " + "removeAll() operation : " + arrlist1);

            ArrayList<Integer> arrlist2 = null;

            System.out.println("Collection Elements" + " to be removed : " + arrlist2);

            System.out.println("\nTrying to pass " + "null as a specified element\n");

            arrlist1.removeAll(arrlist2);

            System.out.println("ArrayList after " + "removeAll() operation : " + arrlist1);
        }

        catch (NullPointerException e)
        {
            System.out.println("Exception thrown : " + e);
        }
    }
}
