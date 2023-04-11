import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class SendMore_Client
{
    public static void main(String[] args)
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {

            System.out.println("Connecting to hello world server");

            ZMQ.Socket socket = context.socket(SocketType.REQ);

            socket.connect("tcp://localhost:5555");

            System.out.println(socket.getLastEndpoint());

            for (int requestNbr = 0; requestNbr != 10; requestNbr++)
            {
                String request = "Hello";

                String req = "Hii";

                String req1 = "Hey";

                String req2 = "Hemant";

                System.out.println("Sending Hello " + requestNbr);

                socket.sendMore(req.getBytes(ZMQ.CHARSET));

                socket.send(request.getBytes(ZMQ.CHARSET));

                byte[] reply = socket.recv();

                System.out.println("Received " + new String(reply, ZMQ.CHARSET) + " " + requestNbr);

                socket.sendMore(req1.getBytes(ZMQ.CHARSET));

                socket.send(req2.getBytes(ZMQ.CHARSET));

                byte[] reply1 = socket.recv();

                System.out.println("Received " + new String(reply1, ZMQ.CHARSET) + " " + requestNbr);
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
