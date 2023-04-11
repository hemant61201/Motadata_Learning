package Push_Pull;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class taskwork
{
    public static void main(String[] args) throws Exception
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            ZMQ.Socket receiver = context.socket(SocketType.PULL);

            receiver.connect("tcp://localhost:5557");

            ZMQ.Socket sender = context.socket(SocketType.PUSH);

            sender.connect("tcp://localhost:5558");

            while (true)
            {
                String string = new String(receiver.recv(0), ZMQ.CHARSET).trim();

                long msec = Long.parseLong(string);

                System.out.flush();

                System.out.print(string + '.');

                Thread.sleep(msec);

                sender.send(ZMQ.MESSAGE_SEPARATOR, 0);
            }
        }
    }
}
