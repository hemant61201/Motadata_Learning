package Sorting_Algorithm;

public class CountingSortNonStable {

    public static void main(String[] args) {

        int[] arr = {2, 4, 5, 6, 8, 2, 9, 8, 10};

        countingSort(arr, 1, 10);

        for (int index = 0; index < arr.length; index++)
        {
            System.out.println(arr[index]);
        }
    }

    public static void countingSort(int[] arr, int min, int max)
    {
        int[] count = new int[(max - min) + 1];

        try
        {
            for (int i = 0; i < arr.length; i++)
            {
                count[arr[i] - min]++;
            }

            int j = 0;

            for (int i = min; i <= max; i++)
            {
                while (count[i - min] > 0)
                {
                    arr[j++] = i;

                    count[i - min]--;
                }
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
