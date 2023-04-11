package Pub_Sub_Envelop;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class psenvsub
{

    public static void main(String[] args)
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            Socket subscriber = context.socket(SocketType.SUB);

            subscriber.connect("tcp://localhost:5563");

            subscriber.subscribe("A".getBytes(ZMQ.CHARSET));

            while (true)
            {
                String address = subscriber.recvStr();

                String contents = subscriber.recvStr();

                System.out.println(address + " : " + contents);
            }
        }
    }
}
