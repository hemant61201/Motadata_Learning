import java.util.ArrayList;
import java.util.Collection;
public class ArrayListContains {

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

            boolean result = arrlist.contains(2);

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

