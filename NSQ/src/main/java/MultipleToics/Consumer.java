package MultipleToics;

import com.github.brainlag.nsq.NSQConfig;
import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

import java.io.IOException;

public class Consumer {

    public static void main(String[] args) throws IOException, InterruptedException {



        NSQLookup lookup = new DefaultNSQLookup();

        lookup.addLookupAddress("localhost", 4161);

        NSQConsumer consumer = new NSQConsumer(lookup, "topic1", "chanel1", (message) -> {


            String rcv = new String(message.getMessage());

            System.out.println(rcv);

            message.finished();


            //message.requeue();
        });

//        NSQConsumer consumer2 = new NSQConsumer(lookup, "topic2" , "channel2", (message2)->{
//
//            String rcv = new String(message2.getMessage());
//
//            System.out.println("consumer2: "+ rcv);
//
//            message2.finished();
//
//
//
//        });



        consumer.start();

//        consumer2.start();

//        Thread.sleep(100000);

        consumer.close();
    }
}
