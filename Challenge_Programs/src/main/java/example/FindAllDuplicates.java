package example;

import java.util.ArrayList;
import java.util.List;

public class FindAllDuplicates {

    public static void main(String[] args)
    {

        try
        {
            int[] nums = new int[]{2,2,7,7,11,15};

            List<Integer> result = new ArrayList<Integer>();

            List<Integer> list1 = new ArrayList<Integer>();

            for(int text:nums)
            {
                list1.add(text);
            }

            for(int index = 0;index < list1.size(); index++)
            {
                if(list1.subList(index+1,list1.size()).contains(list1.get(index)))
                {
                    result.add(list1.get(index));
                }
            }

            System.out.println(result);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
