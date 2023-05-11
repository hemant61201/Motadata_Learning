package com.example.vertx_starter.FutureOperations.Ping;

import io.vertx.core.Context;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class RootClass
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    Future<String> future = vertx.deployVerticle(new PingImplementation(), new DeploymentOptions().setWorker(true));

    future.toCompletionStage().whenComplete((result, err) -> {

      if (err != null)
      {
        System.err.println("Could not resolve verticle");

        err.printStackTrace();
      }

      else
      {
        System.out.println("verticle result => " + result);
      }
    });
//    vertx.deployVerticle(new PingImplementation(), new DeploymentOptions().setWorker(true));
  }
}
