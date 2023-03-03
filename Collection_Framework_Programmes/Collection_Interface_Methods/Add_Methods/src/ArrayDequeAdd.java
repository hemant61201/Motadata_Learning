import java.util.*;

public class ArrayDequeAdd {

    public static void main(String[] args) {

        Collection<String> de_que = new ArrayDeque<String>();

        try
        {
            de_que.add("Hello");

            de_que.add("I'm");

            de_que.add("Hemant");

            System.out.println("ArrayDeque: " + de_que);
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
