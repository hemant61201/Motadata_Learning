package com.example.vertx_starter.Web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;


public class Authentication extends AbstractVerticle
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(new Authentication());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    JWTAuthOptions authConfig = new JWTAuthOptions()
      .setKeyStore(new KeyStoreOptions()
        .setType("jceks")
        .setPath("/home/hemant/Documents/vertx-starter/src/main/resources/keystore.jceks")
        .setPassword("secret"));

    JWTAuth jwt = JWTAuth.create(vertx, authConfig);

    router.route("/login").handler(ctx -> {

      if ("paulo".equals(ctx.request().getParam("username")) && "secret".equals(ctx.request().getParam("password")))
      {
        ctx.response()
          .end(jwt.generateToken(new JsonObject().put("sub", "paulo")));
      }
      else
      {
        ctx.fail(401);
      }
    });

    httpServer.requestHandler(router).listen(8080);

  }
}

