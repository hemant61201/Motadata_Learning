package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllDuplicates {

    public static void main(String[] args) {

        int[] nums = new int[]{2,2,7,7,11,15};

        List<Integer> result = new ArrayList<Integer>();

        List<Integer> list1 = new ArrayList<Integer>();

        for(int text:nums) {
            list1.add(text);
        }

        for(int i = 0;i < list1.size(); i++)
        {
            if(list1.subList(i+1,list1.size()).contains(list1.get(i)))
            {
                result.add(list1.get(i));
            }
        }

        System.out.println(result);

    }
}
