package com.example.vertx_starter.Vertex_Options;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class Verticle2 extends AbstractVerticle
{
  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    System.out.println((String) context.get("name"));
  }
}
