package Pub_Sub;

import java.util.Random;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class wuserver
{
    public static void main(String[] args) throws Exception
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            ZMQ.Socket publisher = context.socket(SocketType.PUB);

            publisher.bind("tcp://*:5556");

            publisher.bind("ipc://weather");

            publisher.setSndHWM(5);

            Random srandom = new Random(System.currentTimeMillis());

            while (true)
            {
                int zipcode, temperature, relhumidity;

                zipcode = 10000 + srandom.nextInt(10000);

                temperature = srandom.nextInt(215) - 80 + 1;

                relhumidity = srandom.nextInt(50) + 10 + 1;

                String update = String.format("%05d %d %d", zipcode, temperature, relhumidity);

                publisher.send(update, ZMQ.NOBLOCK);
            }
        }
    }
}
