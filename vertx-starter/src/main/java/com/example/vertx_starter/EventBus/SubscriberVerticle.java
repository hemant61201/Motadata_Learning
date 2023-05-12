package com.example.vertx_starter.EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class SubscriberVerticle extends AbstractVerticle
{
  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    EventBus eventBus = vertx.eventBus();

    MessageConsumer<String> consumer = eventBus.consumer("message.hemant");

    consumer.handler(message -> System.out.println("Message: "+message.body())).completionHandler(handler ->
    {
      if (handler.succeeded())
      {
        System.out.println("Registration is complete. We are ready to read messages...!");
      }
      else
      {
        System.out.println("Registration is not complete: "+handler.cause());
      }
    });

    for (int i = 0; i < 5; i++)
    {
      consumer.handler(message -> System.out.println("For Loop: Message: "+message.body()));
    }



    vertx.setTimer(5000, h -> {
      consumer.unregister(handler -> {
        if (handler.succeeded())
        {
          System.out.println("Handler unregistered");

          vertx.undeploy(context.deploymentID(), result -> {

            if (result.succeeded())
            {
              System.out.println("Success in undeploy");
            }
            else
            {
              System.out.println("Failed to undeploy: "+result.cause().getMessage());

              System.out.println("DeploymentID: "+context.deploymentID());
            }
          });
        }
        else
        {
          System.out.println("failed to unregister");
        }
      });
    });

    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception
  {
    System.out.println("Consumer stop()");
  }
}
