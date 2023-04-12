package MultipleToics;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.NSQConfig;
import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

import java.io.IOException;

public class Consumer1 {
    public static void main(String[] args) throws IOException {

        NSQLookup lookup = new DefaultNSQLookup();

        lookup.addLookupAddress("localhost", 4161);

        NSQConsumer consumer2 = new NSQConsumer(lookup, "topic2" , "channel2", (message2)->{

            String rcv = new String(message2.getMessage());

            System.out.println("consumer2: "+ rcv);

            message2.finished();



        });

        consumer2.start();

        consumer2.close();
    }
}
