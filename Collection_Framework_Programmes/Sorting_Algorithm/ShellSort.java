package Sorting_Algorithm;

public class ShellSort {

    public static void main(String[] args) {

        int[] intarray = {20, 40, 10, 3, -27, 76, 37};

        try
        {
            for (int gap = intarray.length / 2 ; gap > 0 ; gap /= 2)
            {
                for (int unsortedArrayindex = gap; unsortedArrayindex < intarray.length; unsortedArrayindex++)
                {
                    int newelement = intarray[unsortedArrayindex];

                    int index;

                    for (index = unsortedArrayindex; index >= gap && intarray[index - gap] > newelement; index--)
                    {
                        intarray[index] = intarray[index - 1];
                    }

                    intarray[index] = newelement;

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
}
