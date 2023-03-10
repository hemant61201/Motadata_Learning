package org.example;

public class RunWhileThread extends Thread{

    @Override
    public void run()
    {
        while(true)
        {
            System.out.println("Running");
        }
    }

    public static void main(String[] args)
    {
        RunWhileThread runWhileThread = new RunWhileThread();

        Thread thread = new Thread(runWhileThread);

        thread.start();
    }
}
