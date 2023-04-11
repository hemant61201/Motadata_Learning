package Pub_Sub;

import java.util.StringTokenizer;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class wuclient
{
    public static void main(String[] args)
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            System.out.println("Collecting updates from weather server");

            ZMQ.Socket subscriber = context.socket(SocketType.SUB);

            subscriber.connect("tcp://localhost:5556");

            String filter = (args.length > 0) ? args[0] : "10001 ";

            subscriber.subscribe(filter.getBytes(ZMQ.CHARSET));

            int update_nbr;

            long total_temp = 0;

            for (update_nbr = 0; update_nbr < 100; update_nbr++)
            {
                String string = subscriber.recvStr().trim();

                StringTokenizer sscanf = new StringTokenizer(string, " ");

                int zipcode = Integer.valueOf(sscanf.nextToken());

                int temperature = Integer.valueOf(sscanf.nextToken());

                int relhumidity = Integer.valueOf(sscanf.nextToken());

                total_temp += temperature;
            }

            System.out.println(String.format("Average temperature for zipcode '%s' was %d.", filter, (int)(total_temp / update_nbr)));
        }
    }
}
