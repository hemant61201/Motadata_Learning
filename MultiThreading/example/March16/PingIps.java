package org.example.March16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PingIps
{
    public static void main(String[] args)
    {
        try
        {
            RangeOfIp rangeOfIp = new RangeOfIp();

            String[] allIp = rangeOfIp.rangeIp();

            LinkedHashMap<String,ArrayList<String>> finalResult = new LinkedHashMap<>();

            ExecutorService executorService = Executors.newFixedThreadPool(16);

            List<Future<ArrayList<String>>> futures = new ArrayList<>();

            ArrayList<String> result = new ArrayList<>();

            Instant startTime = Instant.now();

            for (String ip : allIp)
            {
                futures.add(executorService.submit(() -> {

                    try
                    {
                        Process process = Runtime.getRuntime().exec("ping -c 5 " + ip);

                        System.out.println(Thread.currentThread().getName() + " " + ip);

                        try(BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream())))
                        {
                            String pingOutput;

                            int count = 0;

                            boolean flag = false;

                            while ((pingOutput = inputStream.readLine()) != null)
                            {
                                if (pingOutput.equals("--- " + ip + " ping statistics ---"))
                                {
                                    flag = true;
                                }

                                if (flag)
                                {
                                    if (count > 0)
                                    {
                                        result.add(pingOutput);
                                    }

                                    count++;
                                }
                            }

                            return result;
                        }

                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }

                        return null;
                    }

                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }

                     return null;
                }));

                for (Future<ArrayList<String>> future : futures)
                {
                    finalResult.put(ip,future.get());
                }
            }

            executorService.shutdown();

            Instant endTime = Instant.now();

            Duration timeElapsed = Duration.between(startTime, endTime);

            System.out.println(timeElapsed.getSeconds());

            System.out.println(finalResult);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
