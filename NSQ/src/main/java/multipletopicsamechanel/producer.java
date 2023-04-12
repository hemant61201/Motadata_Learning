package multipletopicsamechanel;

import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.exceptions.NSQException;

import java.util.concurrent.TimeoutException;

public class producer {
    public static void main(String[] args) throws NSQException, TimeoutException, InterruptedException {

        NSQProducer producer = new NSQProducer().addAddress("localhost", 4150);

        producer.start();

        String hello = "hi anshu";

        String hello2 = "hi anushka";

        for(int iterator = 0 ; iterator<10 ; iterator++){
            producer.produce("topic", (hello).getBytes());


            System.out.println(hello);
        }

//
        for(int iterator = 0 ; iterator<10 ; iterator++){

            producer.produce("topic" , (hello2).getBytes());

            System.out.println(hello2);
        }
    }
}