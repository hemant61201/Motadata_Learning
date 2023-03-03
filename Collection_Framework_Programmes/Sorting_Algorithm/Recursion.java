package Sorting_Algorithm;

import java.math.BigInteger;

public class Recursion {

    public static BigInteger factorial(BigInteger number)
    {
        if(number.equals(BigInteger.ONE))
        {
            return BigInteger.valueOf(1);
        }
        else
        {
            return number.multiply(factorial(number.subtract(BigInteger.ONE)));
        }
    }

    public static void main(final String... arguments)
    {
        try
        {
            int number = 100000;

            System.out.println("Factorial of " + number + "  : " + factorial(BigInteger.valueOf(number)));

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
