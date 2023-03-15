package org.example.March14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class InputThread implements Runnable
{
    BlockingQueue<String[]> blockingQueue = null;

    public InputThread(BlockingQueue<String[]> queue)
    {
        this.blockingQueue = queue;
    }

    public void run()
    {
        ThreadNotify threadNotify = new ThreadNotify();

        Thread thread1 = new Thread(threadNotify, "Thread1");

        Thread thread2 = new Thread(threadNotify, "Thread2");

        Thread thread3 = new Thread(threadNotify, "Thread3");

        Thread thread4 = new Thread(threadNotify, "Thread4");

        Thread thread5 = new Thread(threadNotify, "Thread5");

        List<Integer> waitingThreads = new ArrayList<>();

        int count = 0;

        while (true)
        {
            try
            {
                String[] inputThreads = this.blockingQueue.take();

                List<String> runningThread = Arrays.asList(inputThreads);

                ArrayList<Integer> waitThread = new ArrayList<>();

                for(int index = 1; index < 6; index++)
                {
                    if(!runningThread.contains(String.valueOf(index)))
                    {
                        waitThread.add(index);
                    }
                }

                if(count == 0)
                {
                    for (String threadno : runningThread)
                    {
                        switch (threadno)
                        {
                            case "1":
                            {
                                thread1.start();
                                break;
                            }

                            case "2":
                            {
                                thread2.start();
                                break;
                            }

                            case "3":
                            {
                                thread3.start();
                                break;
                            }

                            case "4":
                            {
                                thread4.start();
                                break;
                            }

                            case "5":
                            {
                                thread5.start();
                                break;
                            }
                        }
                    }

                    for (int threadno : waitThread)
                    {
                        switch (threadno)
                        {
                            case 1:
                            {
                                waitingThreads.add(threadno);
                                thread1.start();
                                threadNotify.doWait(thread1);
                                break;
                            }

                            case 2:
                            {
                                waitingThreads.add(threadno);
                                thread2.start();
                                threadNotify.doWait(thread2);
                                break;
                            }

                            case 3:
                            {
                                waitingThreads.add(threadno);
                                thread3.start();
                                threadNotify.doWait(thread3);
                                break;
                            }

                            case 4:
                            {
                                waitingThreads.add(threadno);
                                thread4.start();
                                threadNotify.doWait(thread4);
                                break;
                            }

                            case 5:
                            {
                                waitingThreads.add(threadno);
                                thread5.start();
                                threadNotify.doWait(thread5);
                                break;
                            }
                        }
                    }
                    count++;
                }

                else
                {
                    for (String threadnumber : runningThread)
                    {
                        if(waitingThreads.contains(Integer.parseInt(threadnumber)))
                        {
                            switch (threadnumber)
                            {
                                case "1":
                                {
                                    waitingThreads.remove(Integer.valueOf(threadnumber));
                                    threadNotify.doNotify(thread1);
                                    break;
                                }

                                case "2":
                                {
                                    waitingThreads.remove(Integer.valueOf(threadnumber));
                                    threadNotify.doNotify(thread2);
                                    break;
                                }

                                case "3":
                                {
                                    waitingThreads.remove(Integer.valueOf(threadnumber));
                                    threadNotify.doNotify(thread3);
                                    break;
                                }

                                case "4":
                                {
                                    waitingThreads.remove(Integer.valueOf(threadnumber));
                                    threadNotify.doNotify(thread4);
                                    break;
                                }

                                case "5":
                                {
                                    waitingThreads.remove(Integer.valueOf(threadnumber));
                                    threadNotify.doNotify(thread5);
                                    break;
                                }
                            }
                        }
                    }

                    for (int threadno : waitThread)
                    {
                        if(!waitingThreads.contains(threadno))
                        {
                            switch (threadno)
                            {
                                case 1:
                                {
                                    waitingThreads.add(threadno);
                                    threadNotify.doWait(thread1);
                                    break;
                                }

                                case 2:
                                {
                                    waitingThreads.add(threadno);
                                    threadNotify.doWait(thread2);
                                    break;
                                }

                                case 3:
                                {
                                    waitingThreads.add(threadno);
                                    threadNotify.doWait(thread3);
                                    break;
                                }

                                case 4:
                                {
                                    waitingThreads.add(threadno);
                                    threadNotify.doWait(thread4);
                                    break;
                                }

                                case 5:
                                {
                                    waitingThreads.add(threadno);
                                    threadNotify.doWait(thread5);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
