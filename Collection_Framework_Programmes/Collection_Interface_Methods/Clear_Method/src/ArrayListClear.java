import java.util.*;

public class ArrayListClear {

    public static void main(String[] args) {

        Collection<Integer> arrlist = new ArrayList<Integer>();

        try
        {
            arrlist.add(1);

            arrlist.add(2);

            arrlist.add(3);

            for (Integer number : arrlist)
            {
                System.out.println("Number = " + number);
            }

            arrlist.clear();

            System.out.println("The new ArrayList is: " + arrlist);

        }

        catch (UnsupportedOperationException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
