package Collection_Interface_Methods.Equals_Method;

import java.util.Collection;
import java.util.HashSet;
public class EqualMethods {

    public static void main(String[] args)
    {
        Collection<Integer> collection = new HashSet<>();

        Collection<Integer> collection1 = new HashSet<>();

        collection.add(5);

        collection1.add(5);

        collection1.add(8);

        boolean val=collection.equals(collection1);

        System.out.println("Equals methods will return : "+val);
    }
}
