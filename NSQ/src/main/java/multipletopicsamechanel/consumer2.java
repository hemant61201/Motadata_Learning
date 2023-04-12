package multipletopicsamechanel;

import com.github.brainlag.nsq.NSQConfig;
import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

import java.io.IOException;

public class consumer2 {

    public static void main(String[] args) throws IOException, InterruptedException {



        NSQLookup lookup = new DefaultNSQLookup();

        lookup.addLookupAddress("localhost", 4161);

        NSQConsumer consumer = new NSQConsumer(lookup, "topic", "channel2", (message) -> {


            String rcv = new String(message.getMessage());

            System.out.println("consumer2"+ rcv);

            message.finished();

        });




        consumer.start();


        consumer.close();
    }
}
