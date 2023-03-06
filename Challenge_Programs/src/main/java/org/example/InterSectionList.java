package org.example;

import java.util.Collections;
import java.util.LinkedList;

public class InterSectionList {

    public static void main(String[] args) {

        LinkedList<Integer> ll = new LinkedList<>();

        LinkedList<Integer> ll1 = new LinkedList<>();

        ll.add(4);

        ll.add(1);

        ll.add(8);

        ll.add(4);

        ll.add(5);

        ll1.add(5);

        ll1.add(6);

        ll1.add(1);

        ll1.add(8);

        ll1.add(4);

        ll1.add(5);

        for(int i =0; i < ll.size(); i++)
        {
            //System.out.println(ll.subList(i,ll.size()));

            if(Collections.indexOfSubList(ll1,ll.subList(i,ll.size())) != -1)
            {
                System.out.println(ll.get(i));

                break;
            }
        }

    }
}
