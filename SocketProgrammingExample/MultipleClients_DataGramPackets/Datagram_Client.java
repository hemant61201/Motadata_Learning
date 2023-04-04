package MultipleClients_DataGramPackets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;

public class Datagram_Client
{
    public static void main(String[] args)
    {
        try(DatagramSocket datagramSocket = new DatagramSocket(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
        {
            String clientMsg = "";

            System.out.println("Start Sending Msg");

            while (!clientMsg.equals("stop"))
            {
                clientMsg = bufferedReader.readLine();

                DatagramPacket datagramPacket = new DatagramPacket(clientMsg.getBytes(),clientMsg.length(), Inet4Address.getByName("0.0.0.0"),4444);

                datagramSocket.send(datagramPacket);
            }

            System.exit(0);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
