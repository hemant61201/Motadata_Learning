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

    vertx.deployVerticle(new PeriodicTimer());

    Context context = vertx.getOrCreateContext();
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

//    while (true)
//    {
//      System.out.println("hey");
//    }
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    vertx.setPeriodic(1000, id -> {

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

    long timerID = vertx.setTimer(3000, id -> {

      System.out.println(Thread.currentThread().getName());

      System.out.println("3 seconds have passed");

      vertx.undeploy(deploymentID())
        .onComplete(result -> {
          if (result.succeeded())
          {
            System.out.println("Undeployed");
          }
          else
          {
            System.out.println("Failed to undeployed: "+result.cause().getMessage());
          }
        }).onFailure(result -> {
          System.out.println("FAILURE: "+result.getMessage());
        });
    });

    System.out.println("timerID: "+timerID);

    }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception
  {
    System.out.println("Stop method of Periodic");

    stopPromise.complete();
  }
}
