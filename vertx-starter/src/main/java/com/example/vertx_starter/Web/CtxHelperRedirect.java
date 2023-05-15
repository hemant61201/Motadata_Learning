package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class CtxHelperRedirect
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router.route("/some").handler(ctx ->
    {
      ctx.redirect("https://www.youtube.com/");

      ctx.vertx().setTimer(5000, tid -> ctx.reroute("/some"));
    });

    httpServer.requestHandler(router).listen(8080);
  }
}
