package NSQExample;

import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.exceptions.NSQException;

import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws NSQException, TimeoutException, InterruptedException {

        NSQProducer producer = new NSQProducer().addAddress("localhost", 4150);

        producer.start();



        String hello = "hi anshu";

        while(true) {
            producer.produce("Anushka", (hello).getBytes());

            System.out.println(hello);
        }
//        producer.shutdown();
    }
}