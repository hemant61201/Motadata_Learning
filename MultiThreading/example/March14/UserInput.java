package org.example.March14;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

class UserInput implements Runnable {

    BlockingQueue<String[]> blockingQueue = null;

    public UserInput(BlockingQueue<String[]> queue)
    {
        this.blockingQueue = queue;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Enter Thread no. to run as comma sepreated");

                String inputThread = scanner.nextLine();

                String[] runThread = inputThread.split(",");

                this.blockingQueue.put(runThread);
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }

            sleep();
        }
    }

    private void sleep()
    {
        try
        {
            Thread.sleep(1000);
        }

        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
