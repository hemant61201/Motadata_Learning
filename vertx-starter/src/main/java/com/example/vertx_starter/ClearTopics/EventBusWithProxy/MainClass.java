package com.example.vertx_starter.ClearTopics.EventBusWithProxy;

import com.example.vertx_starter.ClearTopics.EventBus.HeatSensor;
import com.example.vertx_starter.ClearTopics.EventBus.HttpServerImpl;
import com.example.vertx_starter.ClearTopics.EventBus.Listener;
import com.example.vertx_starter.ClearTopics.EventBus.SensorData;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class MainClass
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(HeatSensor.class.getName(), new DeploymentOptions().setInstances(4));

    vertx.deployVerticle(DataVerticle.class.getName());

    vertx.deployVerticle(HttpServerImpl.class.getName());
  }
}
