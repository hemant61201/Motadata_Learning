package com.example.vertx_starter.ClearTopics;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Offload extends AbstractVerticle
{
  private final Logger logger = LoggerFactory.getLogger(Offload.class);

  @Override
  public void start() throws Exception
  {
    vertx.setPeriodic(5000, id ->
    {
      vertx.executeBlocking(this::blockingCode, this::resultHandler);
    });
  }

  private void blockingCode(Promise<String> promise)
  {
    logger.info("Blocking code running");
    try
    {
      Thread.sleep(4000);

      logger.info("Done!");

      promise.complete("Ok!");

    }
    catch (InterruptedException e)
    {
      promise.fail(e);
    }
  }
  private void resultHandler(AsyncResult<String> ar)
  {
    if (ar.succeeded())
    {
      logger.info("Blocking code result: {}", ar.result());
    }
    else
    {
      logger.error("Woops", ar.cause());
    }
  }

  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(new Offload());
  }
}
