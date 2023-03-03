import java.util.*;

public class MaxNoSuchElement {
    public static void main(String[] args) {
        try
        {
            List<String> list = new LinkedList<String>();

            System.out.println("Max value is: " + Collections.max(list));
        }

        catch (ClassCastException e)
        {
            System.out.println("Exception thrown : " + e);
        }

        catch (NoSuchElementException e)
        {
            System.out.println("Exception thrown : " + e);
        }
    }
}
