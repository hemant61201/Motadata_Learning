package org.example;

public class GetIdExample extends Thread {

    public void run()
    {
        System.out.println("running...");
    }

    public static void main(String args[])
    {
        try
        {
            GetIdExample getIdExample = new GetIdExample();

            getIdExample.start();

            System.out.println("Name of t1: " + getIdExample.getName());

            System.out.println("Id of t1: "+getIdExample.getId());
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
