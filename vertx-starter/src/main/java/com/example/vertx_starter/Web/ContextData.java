package com.example.vertx_starter.Web;

import io.vertx.core.http.HttpServer;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class ContextData
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    Route route = router.route("/some/path/*");

    Route route1 = router.route("/some/path/other");

    route.handler(ctx -> {

      HttpServerResponse httpServerResponse = ctx.response();

      httpServerResponse.putHeader("Content-Type", "text/plain");

      httpServerResponse.setChunked(true);

      httpServerResponse.write("route3\n");

      ctx.put("foo", "bar");

//      ctx.next();

      ctx.vertx().setTimer(5000, tid -> ctx.next());
    });

    route1.handler(ctx -> {

      String res = ctx.get("foo");

      System.out.println(res);

      HttpServerResponse httpServerResponse = ctx.response();

//      httpServerResponse.putHeader("Content-Type", "text/plain");

      httpServerResponse.end(res);

//      ctx.response().end(res);
    });

    httpServer.requestHandler(router).listen(8080);
  }
}
