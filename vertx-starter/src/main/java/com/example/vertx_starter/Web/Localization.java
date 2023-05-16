package com.example.vertx_starter.Web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.LanguageHeader;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class Localization
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    Route route = router.route("/localized");

    route.handler(ctx ->
    {
      for (LanguageHeader language : ctx.acceptableLanguages())
      {
        switch (language.tag()) {
          case "en":
            ctx.response().end("Hello!");
            return;
          case "fr":
            ctx.response().end("Bonjour!");
            return;
          case "pt":
            ctx.response().end("Olá!");
            return;
          case "es":
            ctx.response().end("Hola!");
            return;
        }
      }

      ctx.response().end("Sorry we don't speak: " + ctx.preferredLanguage());
    });

    httpServer.requestHandler(router).listen(8080);
  }
}
