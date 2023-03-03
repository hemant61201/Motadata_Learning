package Collection_Interface_Methods.Iterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
public abstract class Iterators {

    static int i = 0;

    public static void main(String[] args)
    {
        Collection<String> collection = new ConcurrentLinkedQueue<String>();

        collection.add("Ram");

        collection.add("Sham");

        collection.add("Mira");

        collection.add("Rajesh");

        Iterator<String> iterator = collection.iterator();

        while (iterator.hasNext())
        {
            System.out.println(i++);

            System.out.println(i++ + "." + iterator.next());
        }
    }
}
