package com.example.vertx_starter.ClearTopics;

import io.vertx.core.Vertx;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;


public class ReadFileVertex
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    OpenOptions openOption = new OpenOptions().setRead(true);

    vertx.fileSystem().open("index.html", openOption, ar ->
    {
      if(ar.succeeded())
      {
        AsyncFile asyncFile = ar.result();

        asyncFile.handler(System.out::println)
          .exceptionHandler(Throwable::printStackTrace)
          .endHandler(done -> {
            System.out.println("\n--- DONE");

            vertx.close();
          });
      }

      else
      {
        ar.cause().printStackTrace();
      }
    });

  }
}
