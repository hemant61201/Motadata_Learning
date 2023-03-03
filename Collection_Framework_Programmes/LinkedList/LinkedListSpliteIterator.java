package LinkedList;

import java.util.LinkedList;
import java.util.Spliterator;

public class LinkedListSpliteIterator {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<String> NameOfMovies = new LinkedList<String>();

            NameOfMovies.add("Delhi 6");

            NameOfMovies.add("3 Idiots");

            NameOfMovies.add("Stree");

            NameOfMovies.add("Airlift");

            Spliterator<String> names = NameOfMovies.spliterator();

            System.out.println("list of Movies:");

            names.forEachRemaining((n) -> System.out.println("Movie Name: " + n));
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
