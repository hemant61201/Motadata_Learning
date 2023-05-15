package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

import java.net.http.HttpResponse;

public class HelloWorldServer
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    httpServer.requestHandler(request -> {

      HttpServerResponse httpServerResponse = request.response();

      httpServerResponse.putHeader("content-type", "text/plain");

      httpServerResponse.end("Hello World");
    });

    httpServer.listen(8080);
  }
}
