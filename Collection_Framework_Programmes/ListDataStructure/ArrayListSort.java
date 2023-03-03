package ListDataStructure;

import java.util.ArrayList;
import java.util.Collections;

public class ArrayListSort {

    public static void main(String[] args)
    {

        ArrayList<String> bags = new ArrayList<String>();

        bags.add("pen");

        bags.add("pencil");

        ArrayList<String> boxes = new ArrayList<String>(4);

        boxes.add("pen");

        boxes.add(null);

        boxes.add("paper");

        Collections.sort(boxes);

        System.out.println(boxes);
    }
}
