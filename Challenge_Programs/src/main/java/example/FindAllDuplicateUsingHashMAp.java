package example;

import java.util.ArrayList;
import java.util.HashMap;

public class FindAllDuplicateUsingHashMAp {

    public static void main(String[] args) {

        try
        {
            int[] nums = new int[]{2,2,7,7,11,15};

            ArrayList<Integer> arrlist1 = new ArrayList<>();

            HashMap<Integer,Integer> hashmap1 = new HashMap<>();

            for (int number : nums)
            {
                if(!hashmap1.containsKey(number))
                {
                    hashmap1.put(number,1);
                }
                else
                {
                    arrlist1.add(number);
                }
            }

            System.out.println(arrlist1);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
