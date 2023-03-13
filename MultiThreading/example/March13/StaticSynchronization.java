package org.example.March13;

class Table1{

    synchronized static void printTable(int n)
    {
        for(int index = 1; index <= 10; index++)
        {
            System.out.println(n * index);

            try
            {
                Thread.sleep(400);
            }

            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}

public class StaticSynchronization {

    public static void main(String[] args)
    {
        try
        {
            Thread thread1 = new Thread(() -> Table1.printTable(1));

            Thread thread2 = new Thread(() -> Table1.printTable(10));

            Thread thread3 = new Thread(() -> Table1.printTable(100));

            Thread thread4 = new Thread(() -> Table1.printTable(1000));

            thread1.start();

            thread2.start();

            thread3.start();

            thread4.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
