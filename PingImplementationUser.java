package networkuser;

import java.io.*;

import java.net.*;

import java.lang.*;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public interface PingImplementationUser{

    default String sendPingRequest(String url)
    {

        String result = null;

        int port = 80;

        try
        {
            InetAddress website = InetAddress.getByName(url);

            System.out.println("Sending Ping Request to " + url);

            //  Process p1 = Runtime.getRuntime().exec("ping -c 5" + url);

               // boolean reachable = p1.waitFor(5, SECONDS);

               // boolean reachable = (returnVal != 0);
               // boolean reachable = Reachable(String.valueOf(website), port, 5000);

            if (website.isReachable(5000))
            {
                result = "Host is reachable";
            }

            else
            {
                result = "Sorry ! We can't reach to this host";
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return result;
    }
}
