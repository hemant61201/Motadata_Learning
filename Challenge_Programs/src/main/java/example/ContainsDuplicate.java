package example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ContainsDuplicate {

    public static void main(String[] args)
    {

        try
        {
            int[] nums = new int[]{2,7,7,11,15};

            List<Integer> list1 = new ArrayList<Integer>();

            for(int text:nums)
            {
                list1.add(text);
            }

            HashSet<Integer> hashset1= new HashSet<>(list1);

            if(hashset1.size() == list1.size())
            {
                System.out.println("No Duplicates");
            }

            else
            {
                System.out.println("Duplicate");
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
