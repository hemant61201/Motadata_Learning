package Req_Reply;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class HwClient
{
    public static void main(String[] args)
    {
        try(ZMQ.Context context = ZMQ.context(1))
        {

            System.out.println("Connecting to hello world server");

            ZMQ.Socket socket = context.socket(SocketType.REQ);

            socket.connect("tcp://localhost:5555");

            System.out.println(socket.getLastEndpoint());

            for (int requestNbr = 0; requestNbr != 10; requestNbr++)
            {
                String request = "Hello";

                System.out.println("Sending Hello " + requestNbr);

                socket.send(request.getBytes(ZMQ.CHARSET));

                byte[] reply = socket.recv();

                System.out.println("Received " + new String(reply, ZMQ.CHARSET) + " " + requestNbr);
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}