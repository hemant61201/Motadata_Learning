package Collection_Interface_Methods.Remove;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
public class RemoveIf {

    public static void main(String[] args)
    {
        Collection<Character> collection = new ConcurrentLinkedQueue<Character>();

        char c;

        for(c='A';c<='Z';++c)
        {
            collection.add(c);
        }

        System.out.println("Albabets : "+ collection);

        Predicate<Character> pr= a->(a!='A'&& a!='E'&& a!='I'&& a!='O'&& a!='U' );

        collection.removeIf(pr );

        System.out.println(" Vowels = "+collection);
    }
}
