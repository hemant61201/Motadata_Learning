package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class MetaData
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router
      .route("/metadata/route")

      .putMetadata("metadata-key", "123")

      .handler(ctx -> {

        Route route = ctx.currentRoute();

        String value = route.getMetadata("metadata-key");

        ctx.end(value);
      });

    httpServer.requestHandler(router).listen(8080);
  }
}
