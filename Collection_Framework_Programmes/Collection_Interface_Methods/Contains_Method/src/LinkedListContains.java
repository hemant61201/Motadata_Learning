import java.util.Collection;
import java.util.LinkedList;

public class LinkedListContains {
    public static void main(String[] args) {

        Collection<String> list = new LinkedList<String>();

        try
        {
            list.add("Hello");

            list.add("I'm");

            list.add("Hemant");

            System.out.println("The new List is: " + list);

            boolean result = list.contains("Hemant");

            System.out.println("Is Hemant present in the List: " + result);

        }

        catch (ClassCastException e)
        {
            System.out.println(e.getMessage());
        }

        catch (NullPointerException e)
        {
            System.out.println(e.toString());
        }
    }
}