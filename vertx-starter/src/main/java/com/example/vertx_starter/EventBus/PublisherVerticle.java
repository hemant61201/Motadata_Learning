package com.example.vertx_starter.EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageProducer;

public class PublisherVerticle extends AbstractVerticle
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(PublisherVerticle.class.getName());

    vertx.deployVerticle(SubscriberVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    EventBus eventBus = vertx.eventBus();

    System.out.println("Sending message");

    MessageProducer<String> producer = eventBus.publisher("message.hemant");

//    producer.write("This is Hemant")
//      .onComplete(result -> {
//        if (result.succeeded())
//        {
//          System.out.println("message is send successfully: This is Hemant");
//        }
//        else {
//          System.out.println("Error in sending message: "+result.cause());
//        }
//      }).onFailure(result -> {
//        System.out.println("FAILURE: "+result.getMessage());
//      });
//
//    eventBus.publish("message.hemant", producer);

//    vertx.setTimer(1, handler -> {
//      System.out.println("i am here");
//      producer.write("Hemant",h->{
//          if (h.succeeded())
//          {
//            System.out.println("Message send...");
//          }
//          else
//          {
//            h.cause().printStackTrace();
//          }
//        });
//    });


    vertx.setPeriodic(1000, handler ->
    {
      eventBus.publish("message.hemant", "Hey! this is Hemant");
    });

    startPromise.complete();
  }
}
