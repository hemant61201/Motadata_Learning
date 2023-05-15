package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

import java.nio.Buffer;

public class CtxHelper
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    router.route().handler(ctx ->
    {
      HttpServerResponse httpServerResponse = ctx.response();

      httpServerResponse.putHeader("Content-Type", "application/pdf");

      httpServerResponse.setChunked(true);

      httpServerResponse.sendFile("/home/hemant/Documents/vertx-starter/src/main/resources/HEMANT_VADHAIYA_Resume_03-05-2023-01-02-02 (1).pdf");

      ctx
        .attachment("HEMANT_VADHAIYA_Resume_03-05-2023-01-02-02 (1).pdf")
        .response()
        .end("pdfBuffer");
    });

    httpServer.requestHandler(router).listen(8080);
  }
}
