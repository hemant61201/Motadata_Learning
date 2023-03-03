package ListDataStructure;

import java.util.*;

class ArrayListWithCapacity
{
    public static void main(String[] args)
    {
        int n = 5;

        ArrayList<Integer> arrlist = new ArrayList<Integer>(n);

        for (int i = 1; i <= n; i++)
        {
            arrlist.add(i);
        }

        System.out.println(arrlist);

        arrlist.remove(3);

        System.out.println(arrlist);

        for (int i = 0; i < arrlist.size(); i++)
        {
            System.out.print(arrlist.get(i) + " ");
        }

    }
}

