package com.example.vertx_starter.verticles;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MainVerticle extends AbstractVerticle
{
//  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

    public static void main(String[] args)
    {
        final Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new MainVerticle());
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception
    {
//        LOG.debug("Start {}", getClass().getName());
//        LOG.error("Start {}", getClass().getName());
//        LOG.info("Start {}", getClass().getName());
//        LOG.trace("Start {}", getClass().getName());

        System.out.println("below the logger");



//        vertx.deployVerticle(new VerticleB());

        vertx.deployVerticle(VerticleN.class.getName(),
              new DeploymentOptions()
                .setInstances(1)
                .setConfig(new JsonObject()
                  .put("id", UUID.randomUUID().toString())
                  .put("name", VerticleN.class.getSimpleName())
                )
          ).compose(res->{
            vertx.deployVerticle(new VerticleA());
            return null;
        });

        startPromise.complete();
    }
}
