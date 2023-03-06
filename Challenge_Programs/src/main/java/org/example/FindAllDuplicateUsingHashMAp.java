package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class FindAllDuplicateUsingHashMAp {

    public static void main(String[] args) {

        int[] nums = new int[]{2,2,7,7,11,15};

        ArrayList<Integer> ai = new ArrayList<>();

        HashMap<Integer,Integer> hn = new HashMap<>();

        for (int no : nums)
        {
            if(!hn.containsKey(no))
            {
                hn.put(no,1);
            }
            else
            {
                ai.add(no);
            }
        }

        System.out.println(ai);
    }
}
