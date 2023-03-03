package ListDataStructure;

import java.util.ArrayList;

public class ArrayListLastIndexOf {

    public static void main(String[] args)
    {
        ArrayList<Integer> arr = new ArrayList<Integer>(7);

        arr.add(10);

        arr.add(20);

        arr.add(30);

        arr.add(40);

        arr.add(30);

        arr.add(30);

        arr.add(40);

        System.out.println("The list initially " + arr);

        int element = arr.lastIndexOf(30);

        if (element != -1)
            System.out.println("the lastIndexof of" + " 30 is " + element);

        else
            System.out.println("30 is not present in" + " the list");

        element = arr.lastIndexOf(100);

        if (element != -1)
            System.out.println("the lastIndexof of 100" + " is " + element);

        else
            System.out.println("100 is not present in" + " the list");
    }
}
