package ListDataStructure;

import java.util.ArrayList;

public class ArrayListClear {

    public static void main(String[] args)
    {
        ArrayList<Integer> arr = new ArrayList<Integer>(4);

        arr.add(1);

        arr.add(2);

        arr.add(3);

        arr.add(4);

        System.out.println("The list initially: " + arr);

        arr.clear();

        System.out.println("The list after using clear() method: " + arr);
    }
}
