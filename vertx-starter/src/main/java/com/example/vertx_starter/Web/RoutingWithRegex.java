package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class RoutingWithRegex
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    Route route = router.route("/some/path/*");

    Route route1 = router.route().pathRegex(".*foo");

    route.handler(ctx -> {

      // allow
      // `/some/path/`
      // `/some/path/subdir`
      // `/some/path/subdir/blah.html`
      // `/some/path`

      HttpServerResponse httpServerResponse = ctx.response();

      httpServerResponse.putHeader("Content-Type", "text/plain");

      httpServerResponse.end("route3\n");
    });

    route1.pathRegex("\\/([^\\/]+)\\/([^\\/]+)").handler(ctx -> {

      //allow
      // /some/path/foo
      // /foo
      // /foo/bar/wibble/foo
      // /bar/foo

      // for pathregex link must be http://localhost:8080/foo/abc/

//       .routeWithRegex("\\/(?<productType>[^\\/]+)\\/(?<productID>[^\\/]+)")
//        .handler(ctx -> {
//
//          String productType = ctx.pathParam("productType");
//          String productID = ctx.pathParam("productID");

      String productType = ctx.pathParam("param0");

      String productID = ctx.pathParam("param1");

      System.out.println(productID);

      System.out.println(productType);

      HttpServerResponse httpServerResponse = ctx.response();

      httpServerResponse.putHeader("Content-Type", "text/plain");

      httpServerResponse.end("route3\n");
    });

    httpServer.requestHandler(router).listen(8080).onSuccess(httpServer1 -> System.out.println("server started listening"));
  }
}
