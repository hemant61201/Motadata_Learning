package ListDataStructure;

import java.util.ArrayList;

public class ArrayListEnsureCapacity {

    public static void main(String[] argv) throws Exception
    {
        try
        {
            ArrayList<Integer> arrlist = new ArrayList<Integer>();

            arrlist.add(10);

            arrlist.add(20);

            arrlist.add(30);

            arrlist.add(40);

            System.out.println("ArrayList: " + arrlist);

            arrlist.ensureCapacity(5000);

            System.out.println("ArrayList can now" + " surely store upto" + " 5000 elements.");
        }

        catch (NullPointerException e)
        {
            System.out.println("Exception thrown : " + e);
        }
    }
}
