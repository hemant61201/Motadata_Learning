package com.example.vertx_starter.Web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;


public class HandlingSession extends AbstractVerticle
{

  @Override
  public void start (Promise< Void > startPromise) throws Exception
  {

    HttpServer server = vertx.createHttpServer();

    Router router = Router.router(vertx);

    SessionHandler sessionHandler = SessionHandler.create(LocalSessionStore
      .create(vertx, "myapp.session")).setSessionTimeout(10000);

    router.get("/session/start").handler(sessionHandler).handler(context ->
    {
      Session session = context.session();

      session.put("name", "hemant");

//      context.response().setChunked(true);

      context.end();
    });

    router.get("/session/continue").handler(sessionHandler).handler(context ->
    {
      Session session = context.session();

      System.out.println(session.< String > get("name"));

//      context.response().setChunked(true);

      context.end(session.< String > get("name"));
    });

    router.get("/session/end").handler(sessionHandler).handler(context ->
    {
      System.out.println(context.session().value());

      System.out.println(context.session().id());

      System.out.println(context.session().lastAccessed());

      System.out.println(context.session().timeout());

      System.out.println(context.session().isRegenerated());

      context.session().destroy();

      System.out.println(context.session().value());

      System.out.println(context.session().isDestroyed());

      context.response().setChunked(true);

      context.end();
    });

    server.requestHandler(router).listen(8080).onComplete(ready ->
    {
      if ( ready.succeeded() )
      {
        System.out.println("Server Started Listening!");

        startPromise.complete();
      }
      else
      {
        System.out.println("Some Internal Error Occurred!" + ready.cause().getMessage());

        startPromise.fail("Some Internal Error Occurred!" + ready.cause().getMessage());
      }
    });

  }

  public static void main (String[] args)
  {

    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(HandlingSession.class.getName());
  }

}
