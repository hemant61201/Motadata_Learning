package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class MultipleRoutes
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    Route route = router.route("/some/classpath");

    route.handler(ctx -> {

      HttpServerResponse httpServerResponse = ctx.response();

      httpServerResponse.putHeader("Content-Type", "text/plain");

      httpServerResponse.setChunked(true);

      httpServerResponse.write("route1\n");

      ctx.vertx().setTimer(5000, tid -> ctx.next());
    });

    route.handler(ctx -> {

      HttpServerResponse httpServerResponse = ctx.response();

//      httpServerResponse.putHeader("Content-Type", "text/plain");

      httpServerResponse.write("route2\n");

      ctx.vertx().setTimer(5000, tid -> ctx.next());
    });

    route.handler(ctx -> {

      HttpServerResponse httpServerResponse = ctx.response();

//      httpServerResponse.putHeader("Content-Type", "text/plain");

      httpServerResponse.write("route3\n");

      ctx.response().end();
    });

    httpServer.requestHandler(router).listen(8080);
  }
}
