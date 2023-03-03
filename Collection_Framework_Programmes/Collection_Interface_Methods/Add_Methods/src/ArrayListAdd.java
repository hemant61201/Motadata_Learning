import java.util.*;

public class ArrayListAdd {

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

