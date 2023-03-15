package org.example.March14;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MultipleThreadNotify
{

    public static void main(String[] args) throws InterruptedException
    {
        BlockingQueue<String[]> blockingQueue  = new ArrayBlockingQueue<>(5);

        UserInput userInput = new UserInput(blockingQueue);

        InputThread inputThread = new InputThread(blockingQueue);

        Thread userThread = new Thread(userInput);

        Thread thread = new Thread(inputThread);

        userThread.start();

        thread.start();
    }
}
