package com.example.vertx_starter.ClearTopics.EventBusWithProxy;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface SensorDataService
{

  static SensorDataService create(Vertx vertx)
  {
    return new SensorDataServiceImpl(vertx);
  }

  static SensorDataService createProxy(Vertx vertx, String address) {
    return new SensorDataServiceVertxEBProxy(vertx, address);
  }

  void valueFor(String sensorId, Handler<AsyncResult<JsonObject>> handler);

  void average(Handler<AsyncResult<JsonObject>> handler);
}
