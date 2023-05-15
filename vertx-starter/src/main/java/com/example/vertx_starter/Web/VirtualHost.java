package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class VirtualHost
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router.route().virtualHost("*.vertx.io").handler(ctx ->
    {
      HttpServerResponse httpServerResponse = ctx.response();

      httpServerResponse.putHeader("Content-Type", "text/plain");

      httpServerResponse.end("route3\n");
    });

    httpServer.listen(8099);
  }
}
