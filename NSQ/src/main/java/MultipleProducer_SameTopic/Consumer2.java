package MultipleProducer_SameTopic;

import com.github.brainlag.nsq.NSQConfig;
import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) throws IOException {

         NSQConfig config = new NSQConfig();

//         config.setUserAgent("max-msg-size=1048576");

         config.setMaxInFlight(400);


        NSQLookup lookup = new DefaultNSQLookup();

        lookup.addLookupAddress("localhost", 4161);

        NSQConsumer consumer = new NSQConsumer(lookup, "topic" , "channel1", (message2)->{

            String rcv = new String(message2.getMessage());

            System.out.println("consumer2: "+ rcv);

            message2.finished();



        }, config);

//        NSQConsumer consumer1 = new NSQConsumer(lookup, "topic1" , "channel1", (message2)->{
//
//            String rcv = new String(message2.getMessage());
//
//            System.out.println("consumer1: "+ rcv);
//
//            message2.finished();
//
//
//
//        });


        consumer.start();

//        consumer1.start();

//        consumer.close();

//        consumer1.close();
    }
}
