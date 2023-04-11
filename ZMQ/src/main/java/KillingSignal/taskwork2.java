package KillingSignal;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class taskwork2
{
    public static void main(String[] args) throws InterruptedException
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            ZMQ.Socket receiver = context.socket(SocketType.PULL);

            receiver.connect("tcp://localhost:5557");

            ZMQ.Socket sender = context.socket(SocketType.PUSH);

            sender.connect("tcp://localhost:5558");

            ZMQ.Socket controller = context.socket(SocketType.SUB);

            controller.connect("tcp://localhost:5559");

            controller.subscribe(ZMQ.SUBSCRIPTION_ALL);

            ZMQ.Poller items = context.poller(2);

            items.register(receiver, ZMQ.Poller.POLLIN);

            items.register(controller, ZMQ.Poller.POLLIN);

            while (true)
            {
                items.poll();

                if (items.pollin(0))
                {
                    String message = receiver.recvStr(0);

                    long nsec = Long.parseLong(message);

                    System.out.println(message + '.');

                    System.out.flush();

                    Thread.sleep(nsec);

                    sender.send("", 0);
                }

                if (items.pollin(1))
                {
                    break;
                }
            }
        }
    }
}
