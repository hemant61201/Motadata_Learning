package com.example.vertx_starter.Web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.Router;

public class CookieExample extends AbstractVerticle {

  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(CookieExample.class.getName(), new DeploymentOptions().setInstances(1));
  }

  @Override
  public void start()
  {
    Router router = Router.router(vertx);

    router.route("/books").handler(context -> {

      context.response().addCookie(Cookie.cookie("request", "completed").setHttpOnly(true).setMaxAge(60));

      Cookie cookie = context.request().getCookie("request");

      System.out.println(cookie.getValue());

      context.response().end(cookie.getValue());

    });

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080).onSuccess(ok -> System.out.println("Server listening at Port 8080"));

  }
}
