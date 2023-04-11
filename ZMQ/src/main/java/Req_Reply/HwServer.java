package Req_Reply;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class HwServer
{
    public static void main(String[] args) throws Exception
    {
        try(ZMQ.Context context =  ZMQ.context(1))
        {
            ZMQ.Socket socket = context.socket(SocketType.REP);

            socket.bind("tcp://*:5555");

            System.out.println(socket.getLastEndpoint());

            while (true)

            {
                byte[] reply = socket.recv();

                System.out.println( "Received " + ": [" + new String(reply, ZMQ.CHARSET) + "]");

                Thread.sleep(1000);

                String response = "world";

                socket.send(response.getBytes(ZMQ.CHARSET));

            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}