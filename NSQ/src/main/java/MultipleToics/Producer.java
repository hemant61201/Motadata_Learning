package MultipleToics;

import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.exceptions.NSQException;

import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws NSQException, TimeoutException, InterruptedException {

        NSQProducer producer = new NSQProducer().addAddress("localhost", 4150);

        producer.start();

        String hello = "hi anshu";

        String hello2 = "hi anushka";

        for(int iterator = 0 ; iterator<10 ; iterator++){
            producer.produce("topic1", (hello).getBytes());


            System.out.println(hello);


//            producer.produce("Anushka2" , (hello2).getBytes());
//
//            System.out.println(hello2);
        }


//        NSQProducer producer2 = new NSQProducer().addAddress("localhost", 4150);
//
//        producer2.start();

//        String hello2 = "hi anushka";
//
        for(int iterator = 0 ; iterator<10 ; iterator++){

            producer.produce("topic2" , (hello2).getBytes());

            System.out.println(hello2);
        }




//        producer.shutdown();
    }
}