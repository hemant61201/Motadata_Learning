package org.example;

import com.github.brainlag.nsq.NSQConfig;
import com.github.brainlag.nsq.NSQProducer;

public class NSQProducerExample {
    public static void main(String[] args)
    {
        try
        {
            // create a new NSQ producer
            NSQProducer producer = new NSQProducer();

            // add the NSQD host and port
            producer.addAddress("localhost", 4150);

            // start the producer
            producer.start();

            while(true)
            {
                String s  = new String("Hello NSQ!");

                // publish a message to the "test" topic
                producer.produce("test", s.getBytes());

                System.out.println(s);
            }
            // stop the producer
//            producer.shutdown();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}