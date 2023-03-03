package Vector;
import java.util.*;

public class VectorExample
{
    public static void main(String args[])
    {
        try
        {
            Vector<String> vec = new Vector<String>();

            vec.add("Tiger");

            vec.add("Lion");

            vec.add("Dog");

            vec.add("Elephant");

            vec.addElement("Rat");

            vec.addElement("Cat");

            vec.addElement("Deer");

            System.out.println("Elements are: "+vec);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
