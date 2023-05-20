package com.example.vertx_starter.ClearTopics.EventBusWithProxy;

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;

public class DataVerticle extends AbstractVerticle
{
  @Override
  public void start()
  {
    new ServiceBinder(vertx)
      .setAddress("sensor.data-service")
      .register(SensorDataService.class, SensorDataService.create(vertx));
  }
}
