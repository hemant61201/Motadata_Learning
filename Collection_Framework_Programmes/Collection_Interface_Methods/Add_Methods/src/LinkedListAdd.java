import java.util.Collection;
import java.util.LinkedList;

public class LinkedListAdd {
    public static void main(String[] args) {

        Collection<String> list = new LinkedList<String>();

        try
        {
            list.add("Hello");

            list.add("I'm");

            list.add("Hemant");

            System.out.println("The new List is: " + list);
        }

        catch (UnsupportedOperationException e)
        {
            System.out.println(e.getMessage());
        }

        catch (ClassCastException e)
        {
            System.out.println(e.getMessage());
        }

        catch (NullPointerException e)
        {
            System.out.println(e.toString());
        }

        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }

        catch (IllegalStateException e)
        {
            System.out.println(e.getMessage());
        }
    }
}