package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class PostParm
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router
      .route( HttpMethod.POST, "/catalogue/products/:productType/:productID/")

      .handler(ctx -> {

        String productType = ctx.pathParam("productType");

        String productID = ctx.pathParam("productID");

        System.out.println(productID);

        System.out.println(productType);

        HttpServerResponse httpServerResponse = ctx.response();

        httpServerResponse.putHeader("Content-Type", "text/plain");

        httpServerResponse.end("route3\n");
      });

    httpServer.requestHandler(router).listen(8080);
  }
}
