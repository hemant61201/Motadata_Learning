package ListDataStructure;

import java.util.ArrayList;

public class ArrayListRetainAll {

    public static void main(String[] args)
    {
        ArrayList<String> bags = new ArrayList<String>();

        bags.add("pen");

        bags.add("pencil");

        bags.add("paper");

        ArrayList<String> boxes = new ArrayList<String>();

        boxes.add("pen");

        boxes.add("paper");

        boxes.add("books");

        boxes.add("rubber");

        System.out.println("Bags Contains :" + bags);

        System.out.println("Boxes Contains :" + boxes);

        boxes.retainAll(bags);

        System.out.println("\nAfter Applying retainAll()"+ " method to Boxes\n");

        System.out.println("Bags Contains :" + bags);

        System.out.println("Boxes Contains :" + boxes);
    }
}
