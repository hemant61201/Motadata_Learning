package ListDataStructure;

import java.util.ArrayList;

public class ArrayListAddallWithoutIndex
{
    public static void main(String args[])
    {
        ArrayList arr = new ArrayList();

        arr.add("1");

        arr.add(1);

        ArrayList arr1 = new ArrayList();

        arr1.add("2");

        arr1.add(2);

        System.out.println(arr.addAll(arr1));

        // ************************************************

        ArrayList<Integer> arrlist1 = new ArrayList<Integer>(5);

        arrlist1.add(12);

        arrlist1.add(20);

        arrlist1.add(45);

        arrlist1.add(null);

        System.out.println("Printing list1:");

        for (Integer number : arrlist1)
        {
            System.out.println("Number = " + number);
        }

        ArrayList<Integer> arrlist2 = new ArrayList<Integer>(5);

        arrlist2.add(25);

        arrlist2.add(30);

        arrlist2.add(31);

        arrlist2.add(35);

        System.out.println("Printing list2:");

        for (Integer number : arrlist2)
            System.out.println("Number = " + number);

        arrlist1.addAll(arrlist2);

        System.out.println("Printing all the elements");

        for (Integer number : arrlist1)
            System.out.println("Number = " + number);
    }
}

