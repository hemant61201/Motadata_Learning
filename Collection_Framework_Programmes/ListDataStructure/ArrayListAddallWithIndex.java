package ListDataStructure;

// Java program to illustrate
// boolean addAll(int index, Collection c)

import java.util.ArrayList;

public class ArrayListAddallWithIndex
{
    public static void main(String args[])
    {

        ArrayList<Integer> arrlist = new ArrayList<Integer>(5);

        arrlist.add(12);

        arrlist.add(20);

        arrlist.add(45);

        System.out.println("Printing list1:");

        for (Integer number : arrlist)
            System.out.println("Number = " + number);

        ArrayList<Integer> arrlist2 = new ArrayList<Integer>(5);

        arrlist2.add(25);

        arrlist2.add(30);

        arrlist2.add(31);

        arrlist2.add(35);

        System.out.println("Printing list2:");

        for (Integer number : arrlist2)
            System.out.println("Number = " + number);

        arrlist.addAll(2, arrlist2);

        System.out.println("Printing all the elements");

        for (Integer number : arrlist)
            System.out.println("Number = " + number);
    }
}

