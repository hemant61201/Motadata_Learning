package org.example;

public class ContainsDuplicateUsingArray {

    public static void main(String[] args) {

        int[] nums = new int[]{2,7,7,11,15};

       // Arrays.sort(nums);

        int[] result = new int[Integer.MAX_VALUE];

        boolean flag = false;

        for (int i = 0,j = nums.length -1; j > nums.length / 2 && i < nums.length / 2; i++,j--)
        {
            if(result[nums[i]] == 0 || result[nums[j]] == 0)
            {
                result[nums[i]] = nums[i];

                result[nums[j]] = nums[j];
            }

            else {
                System.out.println("Duplicate");
                flag = true;
                break;
            }
        }

        if(!flag)
        {
            System.out.println("No Duplicate");
        }


    }
}
