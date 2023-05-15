package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class SimpleRouter
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router.route().handler(ctx -> {

      HttpServerResponse httpServerResponse = ctx.response();

      httpServerResponse.putHeader("contain-type", "text/plain");

      httpServerResponse.end("Hello World from Vert.x-Web!");
    });

    httpServer.requestHandler(router).listen(8080);
  }
}
