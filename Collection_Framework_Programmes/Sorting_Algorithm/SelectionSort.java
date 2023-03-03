package Sorting_Algorithm;
public class SelectionSort {

    public static void main(String[] args) {

        int[] intarray = {20, 40, 10, 3, -27, 76, 37};

        try
        {
            for (int unsortedArrayindex = intarray.length - 1; unsortedArrayindex > 0; unsortedArrayindex--)
            {
                int largestindex = 0;

                for(int index = 1; index <= unsortedArrayindex; index++)
                {
                    if(intarray[index] > intarray[largestindex])
                    {
                        largestindex = index;
                    }
                }

                swap(intarray, largestindex, unsortedArrayindex);
            }

            for (int index = 0; index < intarray.length; index++)
            {
                System.out.println(intarray[index]);
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void swap(int[] arr, int i, int j)
    {
        if(i == j)
        {
            return;
        }

        int temp = arr[i];

        arr[i] = arr[j];

        arr[j] = temp;
    }
}
