package com.example.vertx_starter.ClearTopics;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
//import io.vertx.core.impl.logging.Logger;
//import io.vertx.core.impl.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloVerticle extends AbstractVerticle
{
//  static
//  {
//    System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
//  }
  private final Logger logger = LoggerFactory.getLogger(HelloVerticle.class);
  private long counter = 1;

  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(HelloVerticle.class.getName());

//    vertx.deployVerticle(new HelloVerticle(),new DeploymentOptions().setInstances(2));
  }

  @Override
  public void start() throws Exception
  {
    vertx.setPeriodic(5000, id ->
    {
      logger.info("tick");
    });

    vertx.createHttpServer()
      .requestHandler(req ->
      {
        logger.info("Request #{} from {}", counter++ , req.remoteAddress().host());

        req.response().end("Hello!");
      }).listen(8080);

    logger.info("Open http://localhost:8080/");
  }
}

