import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class DatagramSocketQ1_Receiver
{
    public static void main(String[] args)
    {
        Thread sendThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try(DatagramSocket datagramSocket = new DatagramSocket(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
                {
                    String clientMsg = "";

                    System.out.println("Started sending message...");

                    while (!clientMsg.equals("stop"))
                    {
                        clientMsg = bufferedReader.readLine();

                        DatagramPacket datagramPacket = new DatagramPacket(clientMsg.getBytes(), clientMsg.length(), Inet4Address.getByName("127.0.0.1"), 6666);

                        datagramSocket.send(datagramPacket);
                    }

                    System.exit(0);
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        sendThread.start();

        try(DatagramSocket datagramSocket = new DatagramSocket(3333))
        {
            String receiveMsg = "";

            System.out.println("Started receiving message...");

            while (!receiveMsg.equals("stop"))
            {
                byte[] buffer = new byte[1024];

                DatagramPacket datagramPacket = new DatagramPacket(buffer, 1024);

                datagramSocket.receive(datagramPacket);

                String result = new String(datagramPacket.getData(), 0, datagramPacket.getLength());

                System.out.println("Msg from Sender : " + result);

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
