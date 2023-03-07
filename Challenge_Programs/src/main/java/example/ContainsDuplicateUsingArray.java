package example;

public class ContainsDuplicateUsingArray {

    public static void main(String[] args)
    {

        try
        {
            int[] nums = new int[]{2,7,7,11,15};

            // Arrays.sort(nums);

            int[] result = new int[Integer.MAX_VALUE];

            boolean flag = false;

            for (int index = 0,index1 = nums.length -1; index1 > nums.length / 2 && index < nums.length / 2; index++,index1--)
            {
                if(result[nums[index]] == 0 || result[nums[index1]] == 0)
                {
                    result[nums[index]] = nums[index];

                    result[nums[index1]] = nums[index1];
                }

                else
                {
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

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
