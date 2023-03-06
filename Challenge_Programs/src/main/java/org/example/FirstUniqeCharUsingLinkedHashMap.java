package org.example;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FirstUniqeCharUsingLinkedHashMap {

    public static void main(String[] args) {

        String s = "loveleetcode";

        char[] chars = s.toCharArray();

        LinkedHashMap<Character, Integer> hn = new LinkedHashMap<>();

        int value = 1;

        for (char ch : chars)
        {
            if (!hn.containsKey(ch))
            {
                hn.put(ch, value);
            }

            else
            {
                hn.put(ch,value + 1);
            }
        }

        System.out.println(hn);

        for (Map.Entry<Character, Integer> ite : hn.entrySet())
        {
            if(ite.getValue() == 1)
            {
                System.out.print(ite.getKey());

                break;
            }
        }

    }
}
