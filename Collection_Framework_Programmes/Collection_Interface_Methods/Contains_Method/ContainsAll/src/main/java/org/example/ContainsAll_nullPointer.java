package org.example;
import java.util.ArrayDeque;
import java.util.Collection;
public class ContainsAll_nullPointer {

    public static void main(String[] args)
    {
        Collection<String> collection=new ArrayDeque<>();

        collection.add("Himanshu");

        collection.add("Sham");

        collection.add("Rita");

        Collection<Boolean> collection1=new ArrayDeque<>();

        collection1.add(true); // if instead of true i used null give nullpointerException

        System.out.println(collection.containsAll(collection1));
    }
}
