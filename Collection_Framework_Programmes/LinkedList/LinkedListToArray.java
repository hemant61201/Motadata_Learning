package LinkedList;

import java.util.LinkedList;

public class LinkedListToArray {

    public static void main(String[] args)
    {
        try
        {
            LinkedList<String> NameOfMovies = new LinkedList<String>();

            NameOfMovies.add("Delhi 6");

            NameOfMovies.add("3 Idiots");

            NameOfMovies.add("Stree");

            NameOfMovies.add("Airlift");

            String[] arr = new String[5];

            NameOfMovies.toArray(arr);
//            NameOfMovies.toArray();

            for(String elements:arr)
                System.out.println(elements);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
