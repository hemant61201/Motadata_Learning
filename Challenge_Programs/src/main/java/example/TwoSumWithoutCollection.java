package example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TwoSumWithoutCollection {

    public static void main(String[] args) {

        try
        {
            int[] nums = new int[]{2,7,7,11,15};

            int target = 9;

            List<List<Integer>> list1 = new ArrayList<>();

            HashMap<Integer,Integer> hashmap1 = new HashMap<>();

            for(int index = 0; index < nums.length; index++)
            {
                if(hashmap1.containsKey(target - nums[index]))
                {
                    ArrayList<Integer> list = new ArrayList<>();

                    list.add(index,hashmap1.get(target - nums[index]));

                    list1.add(list);
                }
                hashmap1.put(nums[index], index);
            }

            System.out.println(list1);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
