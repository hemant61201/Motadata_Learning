package ListDataStructure;

import java.util.ArrayList;

public class ArrayListIsEmpty {

    public static void main(String[] args)
    {
        ArrayList<Integer> arr = new ArrayList<Integer>(5);

        arr.add(null);

        boolean ans = arr.isEmpty();

        if (ans == true)
            System.out.println("The ArrayList is empty");

        else
            System.out.println("The ArrayList is not empty " + arr.size());

        arr.add(1);

        ans = arr.isEmpty();

        if (ans == true)
            System.out.println("The ArrayList is empty");

        else
            System.out.println("The ArrayList is not empty");
    }
}
