package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class GetParm
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router
      .route(HttpMethod.GET, "/flights/:from-:to")

      .handler(ctx -> {

        String from = ctx.pathParam("from");

        String to = ctx.pathParam("to");

        System.out.println(from);

        System.out.println(to);

        HttpServerResponse httpServerResponse = ctx.response();

        httpServerResponse.putHeader("Content-Type", "text/plain");

        httpServerResponse.end("route3\n");
      });

    httpServer.requestHandler(router).listen(8080);
  }
}
