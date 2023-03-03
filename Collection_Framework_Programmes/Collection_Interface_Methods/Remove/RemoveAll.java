package Collection_Interface_Methods.Remove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
public class RemoveAll {

    public static void main(String[] args)
    {
        Collection<Integer> collection = new ConcurrentLinkedQueue<Integer>();

        List<Integer> list = new ArrayList<Integer>();

        for (int i=1;i<21;i++)
        {
            collection.add(i);
        }

        System.out.println("Total no : "+ collection);

        for (int i=1;i<21;i++)
        {
            int j=i%2;

            if (j != 0)
            {
                list.add(i);
            }
        }

        collection.removeAll(list);

        System.out.println(" Table of 2 : "+collection);
    }
}
