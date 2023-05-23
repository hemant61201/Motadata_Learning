package com.example.vertx_starter.ClearTopics.EdgeServiceWIthFuture;

import com.example.vertx_starter.ClearTopics.EdgeServiceWithCallbacks.CollectorService;
import com.example.vertx_starter.ClearTopics.EdgeServiceWithCallbacks.HeatSensor;
import com.example.vertx_starter.ClearTopics.EdgeServiceWithCallbacks.SnapshotService;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.StringTokenizer;

public class MainClass
{
//  public static Vertx vertx = Vertx.vertx();

  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.eventBus().consumer("te", jm -> {
      var ha = jm.body().toString();
      System.out.println(ha);
    });

    vertx.eventBus().consumer("te", jm -> {
      var ha = jm.body().toString();
      System.out.println(ha);
    });

    vertx.eventBus().consumer("te", jm -> {
      var ha = jm.body().toString();
      System.out.println(ha);
    });

    HashMap da = new HashMap<>();
    da.put(1234,"vicken");
//    da.put(123,"hi");
    vertx.eventBus().send("te", new JsonObject().put("key", da));

    vertx.close();


  }
}
