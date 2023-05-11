package com.example.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class BlockingVerticle extends AbstractVerticle
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(new BlockingVerticle(), new DeploymentOptions().setWorker(true));
  }

  @Override
  public void start() throws Exception {
    Thread.sleep(70000);
    System.out.println("hi");

  }
}
