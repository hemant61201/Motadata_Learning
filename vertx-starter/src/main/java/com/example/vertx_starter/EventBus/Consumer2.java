package com.example.vertx_starter.EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;

public class Consumer2 extends AbstractVerticle
{
  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    EventBus eventBus = vertx.eventBus();

    eventBus.consumer("message.send.hemant", handler ->
    {
      System.out.println("Consumer2: Message: "+handler.body());
    });

    startPromise.complete();
  }
}
