package Sorting_Algorithm;

public class QuickSort {

    public static void main(String[] args) {

        int[] arr = {20, -13, 25, 7, 18, -45};

        quickSort(arr, 0, arr.length);

        for (int index = 0; index < arr.length; index++)
        {
            System.out.println(arr[index]);
        }
    }

    public static void quickSort(int[] arr, int start, int end)
    {
        if((end - start) < 2)
        {
            return;
        }

        int pivotindex = partion(arr, start, end);

        quickSort(arr, start, pivotindex);

        quickSort(arr, pivotindex + 1, end);
    }

    public static int partion(int[] arr, int start, int end)
    {
        int pivot = arr[start];

        int i = start;

        int j = end;

        while(i < j)
        {
            while (i < j && arr[--j] >= pivot);

            if(i < j)
            {
                arr[i] = arr[j];
            }

            while (i < j && arr[++i] <= pivot);

            if(i < j)
            {
                arr[j] = arr[i];
            }
        }

        arr[j] = pivot;

        return j;
    }
}
