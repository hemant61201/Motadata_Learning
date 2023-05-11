package com.example.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class ExecuteBlockingVerticle extends AbstractVerticle
{
  public static void main(String[] args)
  {
    final Vertx vertx = Vertx.vertx();

    System.out.println(Thread.currentThread().getName());

    vertx.deployVerticle(new ExecuteBlockingVerticle()).onComplete(handler->{
        if (handler.succeeded())
          System.out.println("doid" + handler.result());
        else
          System.out.println(handler.cause());
      }
    );
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Context context = vertx.getOrCreateContext();

    System.out.println(Thread.currentThread().getName());
//    Thread.sleep(15000);
    System.out.println(Thread.currentThread().getName());
    vertx.executeBlocking(promise -> {

      System.out.println(Thread.currentThread().getName());
      // Call some blocking API that takes a significant amount of time to return
//      String result = someAPI.blockingMethod("hello");
//      promise.complete(result);
      try {
        Thread.sleep(70000);
        System.out.println("After Sleep");
        promise.complete();

      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

    }).onComplete(res -> {
      System.out.println("The result is: " + res.result());
    });
    startPromise.complete();
  }
}
