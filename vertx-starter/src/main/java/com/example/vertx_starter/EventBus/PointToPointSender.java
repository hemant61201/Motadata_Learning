package com.example.vertx_starter.EventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class PointToPointSender extends AbstractVerticle
{
  public static void main(String[] args)
  {
    Vertx vertx =  Vertx.vertx();

    vertx.deployVerticle(PointToPointSender.class.getName());

    vertx.deployVerticle(Consumer1.class.getName());

    vertx.deployVerticle(Consumer2.class.getName());

    vertx.deployVerticle(Consumer3.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    EventBus eventBus = vertx.eventBus();

    vertx.setPeriodic(1000, handler -> {
      eventBus.send("message.send.hemant", "Hola... this is hemant");
    });

    vertx.setPeriodic(5000, handler -> {
      vertx.undeploy(context.deploymentID())
        .onComplete(result -> {
          if (result.succeeded())
          {
            System.out.println(PointToPointSender.class.getName() + " undeployed");
          }
          else
          {
            System.out.println("Undeployed failed: " + result.cause().getMessage());
          }
        });
    });

    startPromise.complete();
  }
}
