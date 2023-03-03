package ListDataStructure;

import java.util.ArrayList;

public class ArrayListRemoveWithObject {

    public static void main(String[] args)
    {
        ArrayList<String> myAlist = new ArrayList<String>();

        myAlist.add("Geeks");

        myAlist.add("Practice");

        myAlist.add(new String("Quiz"));

        myAlist.add(new String("Quiz"));

        System.out.println("Original ArrayList : " + myAlist);

        myAlist.remove("Quiz");

        System.out.println("Modified ArrayList : " + myAlist);
    }
}
