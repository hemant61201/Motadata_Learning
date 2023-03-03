package ListDataStructure;

import java.util.*;
public class ArrayListToArray {

    public static void main(String args[])
    {
        ArrayList<String> fruitList = new ArrayList<>();

        fruitList.add("Mango");

        fruitList.add("Banana");

        fruitList.add("Apple");

        fruitList.add("Strawberry");

        String[] array = fruitList.toArray(new String[fruitList.size()]);

        System.out.println("Printing Array: "+Arrays.toString(array));

        System.out.println("Printing List: "+fruitList);
    }

}
