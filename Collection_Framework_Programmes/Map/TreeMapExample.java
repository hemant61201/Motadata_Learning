package Map;

import java.util.*;

public class TreeMapExample {

    static TreeMap<Integer, String> tree_map;

    static void create()
    {
        tree_map = new TreeMap<Integer, String>();

        System.out.println("TreeMap successfully" + " created");
    }

    static void insert()
    {
        tree_map.put(10, "Hello");

        tree_map.put(15, "4");

        tree_map.put(20, "Hi");

        tree_map.put(25, "Welcomes");

        tree_map.put(30, "You");

        System.out.println("\nElements successfully" + " inserted in the TreeMap");
    }

    static void search(int key)
    {
        System.out.println("\nIs key \"" + key + "\" present? " + tree_map.containsKey(key));
    }

    static void search(String value)
    {
        System.out.println("\nIs value \"" + value + "\" present? " + tree_map.containsValue(value));
    }

    static void display()
    {
        System.out.println("\nDisplaying the TreeMap:");

        System.out.println("TreeMap: " + tree_map);
    }

    static void traverse()
    {
        System.out.println("\nTraversing the TreeMap:");

        for (Map.Entry<Integer, String> e : tree_map.entrySet())
            System.out.println(e.getKey() + " " + e.getValue());
    }

    public static void main(String[] args)
    {
        try
        {
            create();

            insert();

            search(50);

            search("Hi");

            display();

            traverse();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
