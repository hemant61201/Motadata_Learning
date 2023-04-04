package MultipleClients_DataGramPackets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

public class Datagram_Server
{
    public static void main(String[] args)
    {
        try(DatagramSocket datagramSocket = new DatagramSocket(4444, Inet4Address.getByName("0.0.0.0")))
        {
            String receiveMsg = "";

            System.out.println("Start Receiving Msg");

            while (!receiveMsg.equals("stop"))
            {
                byte[] buff = new byte[1024];

                DatagramPacket datagramPacket = new DatagramPacket(buff, 1024);

                datagramSocket.receive(datagramPacket);

                String result = new String(datagramPacket.getData(),0,datagramPacket.getLength());

                System.out.println("Msg From Sender : " + result);

                if(result.equals("stop"))
                {
                    System.exit(0);
                }
            }

            System.exit(0);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
