package Sorting_Algorithm;

public class MergeSort {

    public static void main(String[] args)
    {
        int[] arr = {20, -13, 25, 7, 18, -45};

        mergeSort(arr, 0, arr.length);

        for (int index = 0; index < arr.length; index++)
        {
            System.out.println(arr[index]);
        }
    }

    public static void mergeSort(int[] arr, int start, int end)
    {
        if((end - start) < 2)
        {
            return;
        }

        int mid = (start + end) / 2;

        mergeSort(arr, start, mid);

        mergeSort(arr, mid, end);

        merge(arr, start, mid, end);
    }

    public static void merge(int[] arr, int start, int mid, int end)
    {
        try
        {
            int i = start;

            int j = mid;

            int tempindex = 0;

            int[] temp = new int[end - start];

            while (i < mid && j < end)
            {
                temp[tempindex++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
            }

            System.arraycopy(arr, i, arr, start + tempindex, mid - i);

            System.arraycopy(temp, 0, arr, start, tempindex);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
