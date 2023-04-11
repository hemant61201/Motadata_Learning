package Push_MultiplePull;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class pull
{
    public static void main(String[] args)
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            ZMQ.Socket receiver = context.socket(SocketType.PULL);

            receiver.connect("tcp://localhost:5557");

            ZMQ.Socket receiver1 = context.socket(SocketType.PULL);

            receiver.connect("tcp://localhost:5557");

            ZMQ.Socket receiver2 = context.socket(SocketType.PULL);

            receiver.connect("tcp://localhost:5557");

            ZMQ.Socket receiver3 = context.socket(SocketType.PULL);

            receiver.connect("tcp://localhost:5557");

            ZMQ.Poller items = context.poller(4);

            items.register(receiver, ZMQ.Poller.POLLIN);

            items.register(receiver1, ZMQ.Poller.POLLIN);

            items.register(receiver2, ZMQ.Poller.POLLIN);

            items.register(receiver3, ZMQ.Poller.POLLIN);

        }
    }
}
