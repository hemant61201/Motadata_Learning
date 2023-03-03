package ListDataStructure;

import java.util.ArrayList;
import java.util.Spliterator;

public class ArrayListSplitIterator {

    public static void main(String[] args)
    {
        ArrayList<String> list = new ArrayList<String>();

        list.add("abc@geeksforgeeks.org");

        list.add("user@geeksforgeeks.org");

        list.add("pqr@geeksforgeeks.org");

        list.add("random@geeksforgeeks.org");

        list.add("randomuser@geeksforgeeks.org");

        Spliterator<String> emails = list.spliterator();

        System.out.println("list of Emails:");

        emails.forEachRemaining((n) -> System.out.println(n));
    }
}
