package com.example.vertx_starter.Vertex_Options;

import com.example.vertx_starter.verticles.VerticleN;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static io.vertx.core.Vertx.vertx;

public class V_Options extends AbstractVerticle
{
  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    System.out.println(Thread.currentThread().getName());

    vertx.deployVerticle(Verticle1.class.getName(),
      new DeploymentOptions()
        .setInstances(1)
//        .setWorker(true)
        .setConfig(new JsonObject()
          .put("id", UUID.randomUUID().toString())
          .put("name", VerticleN.class.getSimpleName())));

    vertx.deployVerticle(new Verticle2());

  }

  public static void main(String[] args)
  {
    VertxOptions vertxOptions = new VertxOptions().setBlockedThreadCheckInterval(5).setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
                                                  .setMaxEventLoopExecuteTime(5).setMaxEventLoopExecuteTimeUnit(TimeUnit.SECONDS);

    Vertx vertx = vertx(vertxOptions);

//    ConfigRetriever retriever = ConfigRetriever.create(vertx);
//
//    retriever.getConfig().onComplete(ar -> {
//      if (ar.failed()) {
//        // Failed to retrieve the configuration
//        System.out.println(ar.cause());
//      } else {
//        JsonObject config = ar.result();
//
//        System.out.println(ar.result());
//      }
//    });

    vertx.deployVerticle(new V_Options());
  }


}
