package com.example.vertx_starter.Web;

import io.vertx.core.http.HttpServer;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class OrderRouter
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router
      .route("/some/path/")

      .order(2)

      .handler(ctx -> {

        HttpServerResponse response = ctx.response();

//        response.setChunked(true);

        response.write("route1\n");

        ctx.next();
      });

    router
      .route("/some/path/")

      .order(0)

      .handler(ctx -> {

        HttpServerResponse response = ctx.response();

        response.setChunked(true);

        response.write("route2\n");

        ctx.next();
      });

    router
      .route("/some/path/")

      .last()

      .handler(ctx -> {

        HttpServerResponse response = ctx.response();

        response.write("route3");

        ctx.response().end();
      });

    httpServer.requestHandler(router).listen(8080);
  }
}
