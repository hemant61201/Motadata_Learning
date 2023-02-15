package networkuser;

import java.io.*;

import java.net.*;

public interface PingImplementationUser {

    default String sendPingRequest(String url)
    {

        String result = null;

        try
        {
            InetAddress website = InetAddress.getByName(url);

            System.out.println("Sending Ping Request to " + url);

            Process p1 = java.lang.Runtime.getRuntime().exec("ping -n 5" + website);

            int returnVal = p1.waitFor();

            boolean reachable = (returnVal==0);

            if (reachable)
            {
                result = "Host is reachable";
            }

            else
            {
                result = "Sorry ! We can't reach to this host";
            }
        }

        catch (UnknownHostException exception)
        {
            exception.printStackTrace();
        }

        catch (IOException exception)
        {
            exception.printStackTrace();
        }

        catch (InterruptedException exception)
        {
            exception.printStackTrace();
        }

        return result;
    }
}
