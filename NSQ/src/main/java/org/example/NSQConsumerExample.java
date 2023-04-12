package org.example;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.NSQMessage;
import com.github.brainlag.nsq.callbacks.NSQMessageCallback;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

public class NSQConsumerExample
{
    public static void main(String[] args)
    {
        try
        {
            NSQLookup lookup = new DefaultNSQLookup();

            lookup.addLookupAddress("localhost", 4161);

            NSQConsumer consumer = new NSQConsumer(lookup,"test", "example", (nsqmessage -> {

                        System.out.println(new String(nsqmessage.getMessage()));
                        nsqmessage.finished();

                }));

            // start the consumer
            consumer.start();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
