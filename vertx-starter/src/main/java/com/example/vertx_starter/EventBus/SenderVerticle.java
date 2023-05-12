package com.example.vertx_starter.EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class SenderVerticle extends AbstractVerticle
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

//    DeploymentOptions deploymentOptions = new DeploymentOptions().setInstances(3);

    vertx.deployVerticle(SenderVerticle.class.getName(), new DeploymentOptions().setInstances(3));

    vertx.deployVerticle(ReciverVerticle.class.getName(), new DeploymentOptions().setInstances(3));
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    EventBus eventBus = vertx.eventBus();

    vertx.setPeriodic(1000, h -> {
      eventBus.request("message.req.hemant", "Hola receiver...")
        .onComplete(handler -> {
          if (handler.succeeded())
          {
            System.out.println("Reply: "+handler.result().body());
          }
          else
          {
            System.out.println("Handler was unable to send request: "+handler.cause().getMessage());
          }
        });
    });
  }
}
