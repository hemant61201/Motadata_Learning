package example;

import java.util.HashSet;
import java.util.LinkedList;

public class RemoveDuplicates {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<Integer> list = new LinkedList<>();

            list.add(4);

            list.add(1);

            list.add(8);

            list.add(4);

            list.add(5);

            HashSet<Integer> hashset1 = new HashSet<>(list);

            System.out.println(hashset1);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
