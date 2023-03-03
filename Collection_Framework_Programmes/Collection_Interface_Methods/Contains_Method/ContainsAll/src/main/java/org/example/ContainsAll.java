package org.example;

import java.util.ArrayDeque;
import java.util.Collection;

public class ContainsAll {
    public static void main(String[] args)
    {
        Collection<String> collection=new ArrayDeque<>();

        collection.add("Himanshu");

        collection.add("Sham");

        collection.add("Rita");

        Collection<String> collection1=new ArrayDeque<>();

        collection1.add("Himanshu");

        collection1.add("Sham");

        collection1.add("Rita");

        System.out.println(collection.containsAll(collection1));
    }
}