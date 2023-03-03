package Collection_Interface_Methods.RetainALl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
public class RetainAll {

    public static void main(String[] args)
    {
        Collection<Character> collection = new ConcurrentLinkedQueue<Character>();

        char i;

        for(i='a';i<='z';++i)
        {
            collection.add(i);
        }

        System.out.println("collection : "+collection);

        List<Character> list = new ArrayList<Character>();

        list.add('a');

        list.add('e');

        list.add('i');

        list.add('o');

        list.add('u');

        collection.retainAll(list);

        System.out.println("Vowels : "+collection);
    }
}
