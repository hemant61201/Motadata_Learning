package Sorting_Algorithm;

import java.util.Collection;

public abstract class BubbleSort implements Collection
{
    public static void main(String[] args) {

        int[] intarray = {20, 40, 10, 3, -27, 76, 37};

        try
        {
            for (int unsortedArrayindex = intarray.length - 1; unsortedArrayindex > 0; unsortedArrayindex--)
            {
                for(int index = 0; index < unsortedArrayindex; index++)
                {
                    if(intarray[index] > intarray[index + 1])
                    {
                        swap(intarray, index, index + 1);
                    }
                }
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
