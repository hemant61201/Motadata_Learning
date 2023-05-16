package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class SubRouters
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router mainRouter = Router.router(vertx);

    Router restApi = Router.router(vertx);

    mainRouter.route("/studentsAPI/*")
      .subRouter(restApi);

    restApi.route("/subject").handler(ctx ->
    {
      ctx.response().end("This is Subject Page");
    });

    restApi.route("/marks").handler(ctx ->
    {
      ctx.response().end("This is Mark Page");
    });

    httpServer.requestHandler(mainRouter).listen(8080);
  }
}
