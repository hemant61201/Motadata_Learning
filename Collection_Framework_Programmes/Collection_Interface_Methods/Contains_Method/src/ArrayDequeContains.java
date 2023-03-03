import java.util.*;

public class ArrayDequeContains {

    public static void main(String[] args) {

        Collection<String> de_que = new ArrayDeque<String>();

        try
        {
            de_que.add("Hello");

            de_que.add("I'm");

            de_que.add("Hemant");

            System.out.println("ArrayDeque: " + de_que);

            boolean result = de_que.contains("Hemant");

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
