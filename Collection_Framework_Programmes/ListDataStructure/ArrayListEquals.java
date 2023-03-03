package ListDataStructure;

import java.util.ArrayList;

public class ArrayListEquals {

    public static void main(String[] args)
    {
        ArrayList<String> bags = new ArrayList<String>();

        bags.add("pen");

        bags.add("pencil");

        bags.add("paper");

        ArrayList<String> boxes = new ArrayList<String>();

        boxes.add("pen");

        boxes.add("paper");

        boxes.add("pencil");

        System.out.println("Bags Contains :" + bags);

        System.out.println("Boxes Contains :" + boxes);

        System.out.println(bags.equals(boxes));

    }
}
