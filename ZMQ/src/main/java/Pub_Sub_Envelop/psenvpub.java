package Pub_Sub_Envelop;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;


public class psenvpub
{

    public static void main(String[] args) throws Exception
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            Socket publisher = context.socket(SocketType.PUB);

            publisher.bind("tcp://*:5563");

            while (true)
            {
                publisher.sendMore("A");

                publisher.send("We don't want to see this");

                publisher.sendMore("B");

                publisher.send("We would like to see this");
            }
        }
    }
}
