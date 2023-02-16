package networkadmin;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.util.Scanner;

public interface PingImplementationAdmin extends RttImplementationAdmin, PacketImplementationAdmin{
    default void PingRequest(String ip)
    {
        String packet = null;

        try
        {
            System.out.println("Enter packet want to send");

            Scanner pack = new Scanner(System.in);

            packet = pack.nextLine();

            Process p = Runtime.getRuntime().exec("ping -c "+ packet + " " + ip);

            try(BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream())))
            {
                String pingoutput = "";

                String result = null;

                while ((pingoutput = inputStream.readLine()) != null)
                {
                    System.out.println(pingoutput);
                }

                System.out.println("Apply Filters");

                System.out.println("1. Want to get rtt details");

                System.out.println("2. Want to get packets details");

                try(BufferedReader number = new BufferedReader(new InputStreamReader(System.in)))
                {
                    while (true)
                    {
                        System.out.println("Enter your Choice");

                        Integer choice = Integer.parseInt(number.readLine());

                        try
                        {
                            if (choice != null)
                            {
                                try
                                {
                                    switch (choice)
                                    {
                                        case 1:
                                        {
                                            rttImplementation(ip);
                                            break;
                                        }

                                        case 2:
                                        {
                                            packetImplementation(ip, packet);
                                            break;
                                        }
                                    }
                                }

                                catch (Exception exception)
                                {
                                    System.out.println("Enter Valid Input");

                                    PingRequest(ip);
                                }
                            }
                        }
                        catch (Exception exception)
                        {
                            System.out.println("Enter Valid Input");

                            PingRequest(ip);
                        }
                    }
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}