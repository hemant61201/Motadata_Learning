package networkuser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public interface Rechability{
    default boolean Reachable(String addr, int openPort, int timeOutMillis)
    {
        try (Socket soc = new Socket())
        {
            soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);

            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }
}

