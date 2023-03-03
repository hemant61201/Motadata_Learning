import java.util.ArrayList;
import java.util.Collection;

public class ArrayListEmpty {
    public static void main(String[] args)
    {
        Collection<String> list = new ArrayList<String>();

        list.add("Hello");

        list.add("I'm");

        list.add("Hemant");

        System.out.println("The new List is: " + list);

        list.clear();

        System.out.println("The new List is: " + list.isEmpty());

    }
}