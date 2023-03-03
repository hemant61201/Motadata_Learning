package ListDataStructure;

import java.util.ArrayList;
public class ArrayListAddWithIndex {

    public static void main(String[] args)
    {
        ArrayList<Integer> arrlist = new ArrayList<Integer>(5);

        arrlist.add(10);

        arrlist.add(22);

        arrlist.add(30);

        arrlist.add(40);

        arrlist.add(3, 35);

        for (Integer number : arrlist)
        {
            System.out.println("Number = " + number);
        }
    }
}
