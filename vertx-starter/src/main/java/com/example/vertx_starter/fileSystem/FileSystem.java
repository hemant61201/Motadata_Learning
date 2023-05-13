package com.example.vertx_starter.fileSystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class FileSystem extends AbstractVerticle
{
  public static void main(String[] args)
  {
    var vertx = Vertx.vertx();

    vertx.deployVerticle(FileSystem.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    var fileSystem = vertx.fileSystem();

    fileSystem.writeFile("hemant.txt", Buffer.buffer("I am Hemant"));

    fileSystem.exists("demo.txt")
      .onComplete(handler ->
      {
        if (handler.succeeded())
        {
          System.out.println("result: " + handler.result());

          if (handler.result())
          {
            fileSystem.delete("demo.txt")
              .onComplete(result ->
              {
                if (result.succeeded())
                {
                  System.out.println("File deleted...");
                } else
                {
                  System.out.println("File not deleted: " + result.cause().getMessage());
                }
              });
          }

          startPromise.complete();
        } else
        {
          System.out.println("file does not exists");
          startPromise.fail(handler.cause().getMessage());
        }
      });
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception
  {
    stopPromise.complete();
  }
}
