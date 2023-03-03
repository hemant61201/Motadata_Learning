import java.util.*;

public class MaxMethod {
    public static void main(String[] args) {
        try
        {
            List<Integer> list = new LinkedList<Integer>();

            list.add(-1);

            list.add(4);

            list.add(-5);

            list.add(1);

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
