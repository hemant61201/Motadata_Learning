package ListDataStructure;

import java.util.ArrayList;

public class ArrayListIndexOf {

    public static void main(String[] args)
    {
        ArrayList<Integer> arr = new ArrayList<Integer>(5);

        arr.add(1);

        arr.add(2);

        arr.add(3);

        arr.add(null);

        arr.add(4);

        System.out.print("The initial values in ArrayList are : ");

        for (Integer value : arr)
        {
            System.out.print(value);

            System.out.print(" ");
        }

        int pos =arr.indexOf(3);

        System.out.println("\nThe element 3 is at index : " + pos);
    }
}
