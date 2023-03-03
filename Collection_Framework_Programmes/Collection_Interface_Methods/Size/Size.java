package Collection_Interface_Methods.Size;

import java.util.Collection;
import java.util.HashSet;
public class Size {

    public static void main(String[] args)
    {
        Collection<Integer> collection = new HashSet<>();

        collection.add(34);

        collection.add(12);

        collection.add(45);

        System.out.print("The elements in Collection : ");

        System.out.println(collection);

        System.out.println("Size of the collection "+collection.size());
    }
}
