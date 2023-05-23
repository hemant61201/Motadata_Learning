package com.example.vertx_starter.LightNMS;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class VertexClass
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(EdgeService.class.getName());
  }
}
