public class LinearSearchExample
{
    public int linearSearch(int[] arr)
    {
        int key = 100000000;

        for(int i=0;i<arr.length;i++)
        {
            if(arr[i] == key)
            {
                return i;
            }
        }

        return -1;
    }
}
