package networkuser;

import java.net.*;

import java.lang.*;

public interface PingImplementationUser{

    default String sendPingRequest(String url)
    {

        String result = null;

        try
        {
            InetAddress website = InetAddress.getByName(url);

            System.out.println("Sending Ping Request to " + url);

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
