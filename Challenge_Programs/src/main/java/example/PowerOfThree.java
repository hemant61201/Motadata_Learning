package example;

public class PowerOfThree {

    public static void main(String[] args)
    {
        try
        {
            int num = 27;

            if(num <= 0)
            {
                System.out.println(false);;
            }

            else
            {
                while(num > 1)
                {
                    if(num % 3 == 0)
                    {
                        num /= 3;
                    }
                    else
                    {
                        System.out.println(false);;
                    }
                }

                System.out.println(false);;
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    // return ( n>0 &&  1162261467%n==0);
}
