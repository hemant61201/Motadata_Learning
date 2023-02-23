package networkadmin;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public interface PacketImplementationAdmin
{
    default void packetImplementation(String ip, String packet)
    {
        try
        {
            String packetcmd = "ping -c " + packet + " " + ip + " | grep " + "'" + packet + " " + "packets'";

            String[] cmd = {
                    "/bin/sh",
                    "-c",
                    packetcmd
            };

            Process p = Runtime.getRuntime().exec(cmd);

            try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream())))
            {
                String packets = inputStream.readLine();

                try
                {
                    String[] result;

                    result = packets.split(",");

                    System.out.println("Packet Transmitted : " + result[0]);

                    System.out.println("Packet Received : " + result[1]);

                    System.out.println("Packet Loss : " + result[2]);

                    System.out.println("Time : " + result[3]);
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

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
