package com.example.vertx_starter.ClearTopics.EdgeServiceWIthFuture;

import com.example.vertx_starter.ClearTopics.EdgeServiceWithCallbacks.CollectorService;
import com.example.vertx_starter.ClearTopics.EdgeServiceWithCallbacks.HeatSensor;
import com.example.vertx_starter.ClearTopics.EdgeServiceWithCallbacks.SnapshotService;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class MainClass
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(com.example.vertx_starter.ClearTopics.EdgeServiceWithCallbacks.HeatSensor.class.getName(), new DeploymentOptions().setConfig(new JsonObject().put("http.port", 3000)));

    vertx.deployVerticle(com.example.vertx_starter.ClearTopics.EdgeServiceWithCallbacks.HeatSensor.class.getName(), new DeploymentOptions().setConfig(new JsonObject().put("http.port", 3001)));

    vertx.deployVerticle(HeatSensor.class.getName(), new DeploymentOptions().setConfig(new JsonObject().put("http.port", 3002)));

    vertx.deployVerticle(SnapshotService.class.getName());

    vertx.deployVerticle(FutureCollectorService.class.getName());
  }
}
