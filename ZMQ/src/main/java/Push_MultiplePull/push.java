package Push_MultiplePull;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class push
{
    public static void main(String[] args)
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            ZMQ.Socket sender = context.socket(SocketType.PUSH);

            sender.bind("tcp://*:5557");

            System.out.println("Press Enter when the workers are ready: ");

            System.in.read();

            System.out.println("Sending tasks to workers\n");

            for (int pollerid = 1; pollerid < 4; pollerid++)
            {
                sender.send(String.valueOf(pollerid), 0);
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
