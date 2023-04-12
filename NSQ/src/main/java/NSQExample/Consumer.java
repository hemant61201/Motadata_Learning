package NSQExample;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

import java.io.IOException;

public class Consumer {
    public static void main(String[] args) throws IOException, InterruptedException {

        NSQLookup lookup = new DefaultNSQLookup();

        lookup.addLookupAddress("localhost", 4161);

        NSQConsumer consumer = new NSQConsumer(lookup, "Anushka", "tests", (message) -> {


            String rcv = new String(message.getMessage());

            System.out.println(rcv);

            message.finished();


            //message.requeue();
        });

        consumer.start();

//        Thread.sleep(100000);

        consumer.close();
    }
}