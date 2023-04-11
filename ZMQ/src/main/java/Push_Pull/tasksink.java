package Push_Pull;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class tasksink
{
    public static void main(String[] args) throws Exception
    {
        try (ZMQ.Context context = ZMQ.context(1))
        {
            ZMQ.Socket receiver = context.socket(SocketType.PULL);

            receiver.bind("tcp://*:5558");

            String string = new String(receiver.recv(0), ZMQ.CHARSET);

            long tstart = System.currentTimeMillis();

            int task_nbr;

            int total_msec = 0;

            for (task_nbr = 0; task_nbr < 100; task_nbr++)
            {
                string = new String(receiver.recv(0), ZMQ.CHARSET).trim();

                if ((task_nbr / 10) * 10 == task_nbr)
                {
                    System.out.print(":");
                }

                else
                {
                    System.out.print(".");
                }
            }

            long tend = System.currentTimeMillis();

            System.out.println("\nTotal elapsed time: " + (tend - tstart) + " msec");
        }
    }
}
