package Collection_Interface_Methods.ToArray;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ToArray {

    public static void main(String[] args)
    {
        Collection<Integer>collection= new ConcurrentLinkedQueue();

        System.out.println("List of even numbers in our collection.");

        for (int i=1;i<=10;i++)
        {
            collection.add(i);
        }

        Integer[] a = new Integer[5];

        Integer[] b =  collection.toArray(a);

        for (int i = 0; i <b.length; i++)
        {
            if (b[i] %2==0)
            {
                System.out.println(b[i]+" ");
            }
        }
    }
}
