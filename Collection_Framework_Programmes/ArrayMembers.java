public class ArrayMembers {
    public static void main(String[] args)
    {
        int[] arr = new int[100000001];

        for (int j = 0; j < arr.length; j++)
        {
            arr[j] = j;
        }

        LinearSearchExample ll = new LinearSearchExample();

        long b = System.currentTimeMillis();

        int a = ll.linearSearch(arr);

        long c = System.currentTimeMillis();

        System.out.println(c - b);

        System.out.println(a);

    }
}
