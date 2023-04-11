package Synchronised_PUB_SUB;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class syncpub
{
    protected static int SUBSCRIBERS_EXPECTED = 10;

    public static void main(String[] args)
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            Socket publisher = context.socket(SocketType.PUB);

            publisher.setLinger(5000);

            publisher.setSndHWM(0);

            publisher.bind("tcp://*:5561");

            Socket syncservice = context.socket(SocketType.REP);

            syncservice.bind("tcp://*:5562");

            System.out.println("Waiting for subscribers");

            int subscribers = 0;

            while (subscribers < SUBSCRIBERS_EXPECTED)
            {
                syncservice.recv(0);

                syncservice.send("", 0);

                subscribers++;
            }

            System.out.println("Broadcasting messages");

            int update_nbr;

            for (update_nbr = 0; update_nbr < 1000000; update_nbr++)
            {
                publisher.send("Rhubarb", 0);
            }

            publisher.send("END", 0);
        }
    }
}
