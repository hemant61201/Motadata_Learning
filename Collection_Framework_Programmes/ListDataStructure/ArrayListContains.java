package ListDataStructure;

import java.util.ArrayList;

public class ArrayListContains {

    public static void main(String[] args)
    {
        ArrayList<StringBuffer> arr = new ArrayList<>(4);

        arr.add(null);

        arr.add(new StringBuffer("Hemant"));

        boolean ans = arr.contains("Hemant");

        ArrayList<String> arr1 = new ArrayList<>();

        arr1.add(null);

        if (ans)
            System.out.println("The list contains Hemant");

        else
            System.out.println("The list does not contains null");

        ans = arr.contains("coding");

        if (ans)
            System.out.println("The list contains coding");

        else
            System.out.println("The list does not contains coding");

        System.out.println(arr.contains(null));

        System.out.println(arr1.contains(null));

    }
}
