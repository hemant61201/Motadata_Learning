package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class ReRouteOnFailure
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router.get("/my-pretty-notfound-handler").handler(ctx ->
    {
      ctx.response()

        .setStatusCode(404)

        .end("NOT FOUND fancy html here!!!");
    });

    router.get().failureHandler(ctx ->
    {
      if (ctx.statusCode() == 404)
      {
        ctx.reroute("/my-pretty-notfound-handler");
      }
      else
      {
        ctx.next();
      }
    });

    httpServer.requestHandler(router).listen(8080);
  }
}
