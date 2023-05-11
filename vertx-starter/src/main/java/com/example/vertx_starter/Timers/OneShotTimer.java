package com.example.vertx_starter.Timers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class OneShotTimer extends AbstractVerticle
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(new OneShotTimer());

    vertx.setTimer(2500, id ->
    {
      System.out.println(Thread.currentThread().getName());

      System.out.println(Thread.currentThread().getState());
    });

    System.out.println(Thread.currentThread().getName());

    System.out.println(Thread.currentThread().getState());

//    while (true)
//    {
//      System.out.println("hey");
//    }

  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    long timerID = vertx.setTimer(3000, id -> {
      System.out.println("3 seconds have passed");
    });

    System.out.println("timerID: "+timerID);

    timerID = vertx.setTimer(2000, id -> {
      System.out.println("2 seconds have passed");
    });

    System.out.println("timerID: "+timerID);

//    while (true)
//    {
//      System.out.println(Thread.currentThread().getName());
//    }
  }
}
