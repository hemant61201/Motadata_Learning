import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AddAll {
    public static void main(String[] args) {

        Collection<String> arrlist = new ArrayList<String>();

        try
        {
            arrlist.add("Hi");

            arrlist.add("Hemant");

            System.out.println("arrlist before operation : " + arrlist);

            Collections.addAll(arrlist, "1", "2", null);

            //NullPointer Exception

            // Collections.addAll(null, arrlist);

            System.out.println("arrlist after operation : " + arrlist);
        }

        catch (NullPointerException e)
        {
            System.out.println(e.getMessage());
        }

        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}