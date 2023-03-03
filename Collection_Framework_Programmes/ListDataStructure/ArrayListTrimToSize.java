package ListDataStructure;

import java.util.ArrayList;

public class ArrayListTrimToSize {

    public static void main(String[] args)
    {
        ArrayList<Integer> arr = new ArrayList<Integer>(9);

        arr.add(2);

        arr.add(4);

        arr.add(5);

        arr.add(6);

        arr.add(11);

        arr.trimToSize();

        System.out.println("The List elements are:");

        for (Integer number : arr)
        {
            System.out.println("Number = " + number);
        }

    }
}
