package com.example.vertx_starter.Timers;

import com.example.vertx_starter.FutureOperations.Ping.PingImplementation;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class PeriodicTimer extends AbstractVerticle
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(new PeriodicTimer()).onComplete(handler ->
    {
      if(handler.succeeded())
      {
        System.out.println("actual id : " + handler.result());
      }
      else
      {
        System.out.println(handler.cause());
      }
    });


//    System.out.println(vertx.deploymentIDs());

//    vertx.setPeriodic(3000, id -> {
//      System.out.println(Thread.currentThread().getName());
//
//      System.out.println(Thread.currentThread().getState());
//
//      System.out.println("hi");
//    });
//
//
//    long timerID = vertx.setTimer(3000, id -> {
//
//      System.out.println(Thread.currentThread().getName());
//
//      System.out.println("3 seconds have passed");
//
//      vertx.undeploy(context.deploymentID())
//        .onComplete(result -> {
//          if (result.succeeded())
//          {
//            System.out.println("Undeployed");
//          }
//          else
//          {
//            System.out.println("Failed to undeployed: "+result.cause().getMessage());
//          }
//        }).onFailure(result -> {
//          System.out.println("FAILURE: "+result.getMessage());
//        });
//    });
//
//    System.out.println("timerID: "+timerID);
//
//    while (true)
//    {
//      System.out.println("hey");
//    }
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {

    vertx.setPeriodic(3000, id -> {

//      try {
//        Thread.sleep(2000);
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }

      System.out.println(Thread.currentThread().getName());

      System.out.println(Thread.currentThread().getState());
//
//      while (true)
//      {
//        System.out.println("hi verticle");
//      }
    });

    long timerID = vertx.setTimer(5000, id -> {

      System.out.println(Thread.currentThread().getName());

      System.out.println("3 seconds have passed");

      System.out.println(deploymentID());

      vertx.undeploy(deploymentID())
        .onComplete(result -> {
          System.out.println("In Undeploy COmplete!");
          if (result.succeeded())
          {
            System.out.println("Undeployed");
          }
          else
          {
            System.out.println("Failed to undeployed: "+result.cause().getMessage());
          }
        });
    });

    System.out.println("timerID: "+timerID);

    startPromise.complete();
//    startPromise.fail("");

    }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception
  {
    System.out.println("Stop method of Periodic");

    stopPromise.complete();
  }
}
