package example;

import java.util.ArrayList;
import java.util.List;

public class TwoSum {

    public static void main(String[] args)
    {
        try
        {
            int[] nums = new int[]{2,7,7,11,15};

            int target = 9;

            List<Integer> list1 = new ArrayList<Integer>();

            for(int text:nums)
            {
                list1.add(text);
            }

            for (int index = 1; index < nums.length; index++)
            {
                int number = target - nums[index];

                if(list1.contains(number))
                {
                    System.out.println(index + "," + list1.indexOf(number));
                }
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }


    }
}
