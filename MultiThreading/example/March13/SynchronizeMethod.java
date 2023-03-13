package org.example.March13;

class Table
{
    synchronized void printTable(int n)
    {
        try
        {
            for(int index = 1; index <= 5; index++)
            {
                System.out.println(n * index);

                Thread.sleep(400);
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}

class MyThread1 extends Thread{
    Table table1;
    MyThread1(Table t)
    {
        this.table1 = t;
    }

    public void run()
    {
        table1.printTable(5);
    }
}

class MyThread2 extends Thread{
    Table table2;
    MyThread2(Table t)
    {
        this.table2 = t;
    }

    public void run()
    {
        table2.printTable(100);
    }
}

public class SynchronizeMethod
{
    public static void main(String[] args)
    {
        try
        {
            Table tableObj = new Table();

            MyThread1 thread1 = new MyThread1(tableObj);

            MyThread2 thread2 = new MyThread2(tableObj);

            thread1.start();

            thread2.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
