package org.example;

import java.util.*;
import java.util.HashSet;
import java.util.stream.*;

public class TwoSum {

    public static void main(String[] args) {

        int[] nums = new int[]{2,7,7,11,15};

        int target = 9;

        List<Integer> list1 = new ArrayList<Integer>();

        for(int text:nums) {
            list1.add(text);
        }

        for (int i = 1; i < nums.length; i++)
        {
            int a = target - nums[i];

            if(list1.contains(a))
            {
                System.out.println(i + "," + list1.indexOf(a));
            }
        }
    }
}
