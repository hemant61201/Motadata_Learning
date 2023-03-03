package ListDataStructure;

import java.util.ArrayList;

public class ArrayListRemoveWithIndex {

    public static void main(String[] args) {

        ArrayList<String> myAlist = new ArrayList<String>();

        myAlist.add("Geeks");

        myAlist.add("Practice");

        myAlist.add("Quiz");

        System.out.println("Original ArrayList : " + myAlist);

        myAlist.remove(2);

        System.out.println("Modified ArrayList : " + myAlist);
    }
}
