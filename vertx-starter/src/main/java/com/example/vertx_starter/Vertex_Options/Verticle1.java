package com.example.vertx_starter.Vertex_Options;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class Verticle1 extends AbstractVerticle
{
  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
//    System.out.println("Start " + getClass().getName() + " on thread " + Thread.currentThread().getName() + " config " + config().toString());

    System.out.println(Thread.currentThread().getName());

//    Thread.sleep(2000);
    context.put("name", "hemant");

    System.out.println("hi");
  }
}
