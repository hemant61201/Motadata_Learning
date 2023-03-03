package Sorting_Algorithm;

public class InsertionSort {

    public static void main(String[] args) {

        int[] intarray = {20, 40, 10, 3, -27, 76, 37};

        try
        {
            for (int unsortedArrayindex = 1; unsortedArrayindex < intarray.length; unsortedArrayindex++)
            {
                int newelement = intarray[unsortedArrayindex];

                int index;

                for (index = unsortedArrayindex; index > 0 && intarray[index - 1] > newelement; index--)
                {
                    intarray[index] = intarray[index - 1];
                }

                intarray[index] = newelement;

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
}
