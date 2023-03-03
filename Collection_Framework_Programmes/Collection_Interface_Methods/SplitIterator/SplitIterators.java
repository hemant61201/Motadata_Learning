package Collection_Interface_Methods.SplitIterator;

import java.util.Collection;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentLinkedQueue;
public class SplitIterators {

    static int i=1;

    public static void main(String[] args)
    {
        Collection<Character> collection = new ConcurrentLinkedQueue();

        for (char i='A';i<='Z';i++)
        {
            collection.add(i);
        }

        System.out.print("Values : ");

        Spliterator<Character>str = collection.spliterator();

        while(str.tryAdvance((n)->System.out.print(n+" ")));

    }
}
