package com.example.vertx_starter.Web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class SessionExample extends AbstractVerticle
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(SessionExample.class.getName(), new DeploymentOptions().setInstances(1));
  }

  @Override
  public void start() {

    Router router = Router.router(vertx);

    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx, "session")).setCookieless(true).setSessionTimeout(20000));

    router.get("/session").handler(routingContext -> {
      Session session = routingContext.session();
      routingContext.response().end(new JsonObject()
        .put("id", session.get("id"))
        .put("password", session.get("password"))
        .encodePrettily());
    });


    router.post("/session").produces("application/json").handler(context -> {

      Session session = context.session();

      System.out.println(session.timeout());

      session.put("id", "hemant");

      session.put("password", "hemant1234");

      context.response().end(new JsonObject()
        .put("id", session.get("id"))
        .put("password", session.get("password"))
        .encodePrettily());

    });

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080).onSuccess(ok -> System.out.println("Server listening at Port 8080"));

  }
}
