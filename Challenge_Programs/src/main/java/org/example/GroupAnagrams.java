package org.example;

import java.util.*;

public class GroupAnagrams {

    public static void main(String[] args) {

        String[] strs = {"eat","tea","tan","ate","nat","bat","tae"};

        HashMap<String, String> hs = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);

            if (hs.containsKey(sorted)) {
                HashMap<String, String> hs1 = new HashMap<>();

                hs1.put(sorted, str);

                hs1.forEach(
                        (key, value)
                                -> hs.merge(
                                key,
                                value,
                                (v1, v2)
                                        -> v1.equalsIgnoreCase(v2)
                                        ? v1
                                        : v1 + ", " + v2));
            } else {
                hs.put(sorted, str);
            }
        }

        Iterator<Map.Entry<String, String>> hsiterator = hs.entrySet().iterator();

        String[] arr = new String[strs.length];

        int i = 0;

        ArrayList<String> result = new ArrayList<>();

        while (hsiterator.hasNext())
        {
            Map.Entry mapElement = (Map.Entry) hsiterator.next();

            arr[i] = (String) mapElement.getValue();

            result.add("[" + arr[i] + "]");

            i++;
        }

        System.out.println(result);

        System.out.println(hs);
    }
}
