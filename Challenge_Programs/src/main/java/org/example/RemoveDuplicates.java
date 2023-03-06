package org.example;

import java.util.HashSet;
import java.util.LinkedList;

public class RemoveDuplicates {

    public static void main(String[] args) {

        LinkedList<Integer> ll = new LinkedList<>();

        ll.add(4);

        ll.add(1);

        ll.add(8);

        ll.add(4);

        ll.add(5);

        HashSet<Integer> hs = new HashSet<>(ll);

        System.out.println(hs);
    }
}
