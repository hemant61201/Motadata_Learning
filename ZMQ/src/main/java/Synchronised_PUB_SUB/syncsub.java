package Synchronised_PUB_SUB;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class syncsub
{

    public static void main(String[] args)
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            Socket subscriber = context.socket(SocketType.SUB);

            subscriber.connect("tcp://localhost:5561");

            subscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);

            Socket syncclient = context.socket(SocketType.REQ);

            syncclient.connect("tcp://localhost:5562");

            syncclient.send(ZMQ.MESSAGE_SEPARATOR, 0);

            syncclient.recv(0);

            int update_nbr = 0;

            while (true)
            {
                String string = subscriber.recvStr(0);

                if (string.equals("END"))
                {
                    break;
                }

                update_nbr++;
            }

            System.out.println("Received " + update_nbr + " updates.");
        }
    }
}
