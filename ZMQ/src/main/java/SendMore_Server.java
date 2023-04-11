import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class SendMore_Server
{
    public static void main(String[] args) throws Exception
    {
        try(ZMQ.Context context =  ZMQ.context(1))
        {
            ZMQ.Socket socket = context.socket(SocketType.REP);

            socket.bind("tcp://*:5555");

            System.out.println(socket.getLastEndpoint());

            while (true)

            {
                byte[] reply = socket.recv();

                byte[] reply1 = socket.recv();

                if(new String(reply, ZMQ.CHARSET) == "Hey")
                {
                    System.out.println( "Received " + ": [" + new String(reply, ZMQ.CHARSET) + "]");

                    String response = "world";

                    socket.send(response.getBytes(ZMQ.CHARSET));
                }

                System.out.println( "Received " + ": [" + new String(reply, ZMQ.CHARSET) + "]");

                Thread.sleep(1000);

                String response = "";

                socket.send(response.getBytes(ZMQ.CHARSET));

            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
