package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class FirstUniqeChar {

    public static void main(String[] args) {

        String s = "loveleetcode";

        char[] chars = s.toCharArray();

        Queue<Character> qc = new ArrayDeque<>();

        for(char ch : chars)
        {
            if(qc.contains(ch))
            {
                qc.remove(ch);
            }

            qc.add(ch);
        }

        System.out.println(s.indexOf(qc.peek()));
    }
}
