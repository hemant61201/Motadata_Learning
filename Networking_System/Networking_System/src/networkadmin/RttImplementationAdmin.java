package networkadmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface RttImplementationAdmin {

    default void rttImplementation(String ip)
    {
        try
        {
            //System.out.println("ping -c 5 " + ip + " | grep 'rtt'");

            Process p = Runtime.getRuntime().exec("ping -c 5 " + ip + " | grep rtt");

            try(BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream())))
            {
                String rtt = "";

                if ((rtt = inputStream.readLine()) != null)
                {
                    System.out.println(rtt);
                }
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
