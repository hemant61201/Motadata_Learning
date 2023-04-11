package KillingSignal;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class tasksink2
{
    public static void main(String[] args) throws Exception
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            ZMQ.Socket receiver = context.socket(SocketType.PULL);

            receiver.bind("tcp://*:5558");

            ZMQ.Socket controller = context.socket(SocketType.PUB);

            controller.bind("tcp://*:5559");

            receiver.recv(0);

            long tstart = System.currentTimeMillis();

            int task_nbr;

            for (task_nbr = 0; task_nbr < 100; task_nbr++)
            {
                receiver.recv(0);

                if ((task_nbr / 10) * 10 == task_nbr)
                {
                    System.out.print(":");
                }

                else
                {
                    System.out.print(".");
                }

                System.out.flush();
            }

            long tend = System.currentTimeMillis();

            System.out.println("Total elapsed time: " + (tend - tstart) + " msec");

            controller.send("KILL", 0);

            Thread.sleep(1);
        }
    }
}
