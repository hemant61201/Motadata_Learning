package com.example.vertx_starter.EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;

public class ReciverVerticle extends AbstractVerticle
{
  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    EventBus eventBus = vertx.eventBus();

    eventBus.consumer("message.req.nikunj", handler -> {
      System.out.println("Message: "+handler.body());

      handler.reply("Hola sender... message received");
    });

    startPromise.complete();
  }
}
