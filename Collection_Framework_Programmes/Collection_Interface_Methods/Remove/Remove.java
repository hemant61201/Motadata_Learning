package Collection_Interface_Methods.Remove;

import java.util.ArrayDeque;
import java.util.Collection;
public class Remove {

    public static void main(String[] args)
    {
        Collection<String> collection = new ArrayDeque<String>() {};

        collection.add("Reema");

        collection.add("Geetanajli");

        collection.add(null);

        for (String i:collection)
        {
            System.out.println(i);
        }

        boolean val=collection.remove("ABC");

        System.out.println("Remove method will return : "+val);
    }
}
