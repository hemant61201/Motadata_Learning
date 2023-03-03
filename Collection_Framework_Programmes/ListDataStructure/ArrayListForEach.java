package ListDataStructure;

import java.util.ArrayList;

public class ArrayListForEach {

    public static void main(String[] args)
    {
        ArrayList<String> students = new ArrayList<String>();

        students.add("Ram");

        students.add("Mohan");

        students.add("Sohan");

        students.add("Rabi");

        System.out.println("list of Students:");

        students.forEach((n) -> System.out.println(n));
    }

}
