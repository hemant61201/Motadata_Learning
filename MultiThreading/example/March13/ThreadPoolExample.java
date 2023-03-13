package org.example.March13;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class Tasks implements Runnable
{
    private String taskName;

    public Tasks(String str)
    {
        taskName = str;
    }

    public void run()
    {
        try
        {
            for (int index = 0; index <= 5; index++)
            {
                if (index == 0)
                {
                    Date date = new Date();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh : mm : ss");

                    System.out.println("Initialization time for the task name: "+ taskName + " = " + simpleDateFormat.format(date));
                }

                else
                {
                    Date date = new Date();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh : mm : ss");

                    System.out.println("Time of execution for the task name: " + taskName + " = " +simpleDateFormat.format(date));
                }

                Thread.sleep(1000);
            }

            System.out.println(taskName + " is complete.");
        }

        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}

public class ThreadPoolExample
{
    static final int MAX_TH = 3;

    public static void main(String[] args)
    {
        Runnable runnable1 = new Tasks("task 1");

        Runnable runnable2 = new Tasks("task 2");

        Runnable runnable3 = new Tasks("task 3");

        Runnable runnable4 = new Tasks("task 4");

        Runnable runnable5 = new Tasks("task 5");

        ExecutorService pool = Executors.newFixedThreadPool(MAX_TH);

        pool.execute(runnable1);

        pool.execute(runnable2);

        pool.execute(runnable3);

        pool.execute(runnable4);

        pool.execute(runnable5);

        pool.shutdown();
    }
}

