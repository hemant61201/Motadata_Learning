package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class ReRoute
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router.get("/some/path").handler(ctx ->
    {
      ctx.put("foo", "bar");

      ctx.next();
    });

    router.get("/some/path/B").handler(ctx ->
    {
      String bar = ctx.get("foo");

      ctx.response().end(bar);
    });

    router.get("/some/path").handler(ctx ->
    {
      ctx.reroute("/some/path/B");
    });

    httpServer.requestHandler(router).listen(8080);
  }
}
