package networkadmin;

import java.io.BufferedReader;

import java.io.InputStreamReader;

public interface PingImplementationAdmin extends RttImplementationAdmin{
    default void PingRequest(String ip)
    {
        try
        {
            Process p = Runtime.getRuntime().exec("ping -c 5 " + ip);

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
                    System.out.println("Enter your Choice");

                    Integer choice = Integer.parseInt(number.readLine());

                    try
                    {
                        if(choice != null)
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
                                    break;
                                }
                            }
                        }
                    }

                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }

                System.exit(0);
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