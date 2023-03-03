package Sorting_Algorithm;

import java.util.Arrays;

public class SortUsingJdk {

    public static void main(String[] args) {

        int[] array = { 4725, 4586, 1330, 8792, 1594, 5729 };

        Arrays.parallelSort(array);

        for (int i = 0; i < array.length; i++)
        {
            System.out.println(array[i]);
        }
    }
}
