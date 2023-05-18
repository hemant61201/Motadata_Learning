package com.example.vertx_starter.ClearTopics.EventBus;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class MainClass
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(HeatSensor.class.getName(), new DeploymentOptions().setInstances(4));

    vertx.deployVerticle(Listener.class.getName());

    vertx.deployVerticle(SensorData.class.getName());

    vertx.deployVerticle(HttpServerImpl.class.getName());
  }
}
