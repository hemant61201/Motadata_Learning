package ListDataStructure;

import java.util.ArrayList;

public class ArrayListToArrayWithStramApi {

    public static void main(String[] args)
    {
        ArrayList<Integer> al = new ArrayList<Integer>();

        al.add(10);

        al.add(20);

        al.add(30);

        al.add(40);

        int[] arr = al.stream().mapToInt(i -> i).toArray();

        for (Integer x : arr)
            System.out.print(x + " ");

    }
}
