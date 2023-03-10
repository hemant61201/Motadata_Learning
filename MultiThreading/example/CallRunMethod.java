package org.example;

import java.time.Year;

public class CallRunMethod
{
    public static void main(String[] args)
    {
        try
        {
            Test run1 = new Test("name1");

            Test run2 = new Test("name2");

            run1.start();

            run2.start();

            Thread thread1 = new Thread(run1, "Thread1");

            thread1.start();

            run1.run();

            run2.run();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
