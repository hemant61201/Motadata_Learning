package multipletopicsamechanel;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.NSQConfig;
import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

import java.io.IOException;

public class consumer1 {
    public static void main(String[] args) throws IOException {

        NSQLookup lookup = new DefaultNSQLookup();

        lookup.addLookupAddress("localhost", 4161);

        NSQConsumer consumer = new NSQConsumer(lookup, "topic" , "channel1", (message2)->{

            String rcv = new String(message2.getMessage());

            System.out.println("consumer1: "+ rcv);

            message2.finished();



        });

        consumer.start();

        consumer.close();
    }
}
