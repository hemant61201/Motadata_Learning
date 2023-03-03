package Collection_Interface_Methods.HashCode;

import java.util.Collection;
import java.util.HashSet;
public class HashCode {

    public static void main(String[] args)
    {
        Collection<String> collection = new HashSet<>();

        collection.add("Reema");

        collection.add("Rahul");

        int val=collection.hashCode();

        System.out.println("Hash Code : "+val);
    }
}
