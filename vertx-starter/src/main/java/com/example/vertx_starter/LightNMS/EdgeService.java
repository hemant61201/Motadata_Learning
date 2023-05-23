package com.example.vertx_starter.LightNMS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.authentication.UsernamePasswordCredentials;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.auth.properties.PropertyFileAuthentication;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.FormLoginHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

import java.util.ArrayList;
import java.util.List;

public class EdgeService extends AbstractVerticle
{
  @Override
  public void start()
  {
    Router router = Router.router(vertx);

    StaticHandler staticHandler = StaticHandler.create().setIndexPage("login.jsp");

    router.route().handler(staticHandler);

    router.route().handler(BodyHandler.create());

    JWTAuthOptions authConfig = new JWTAuthOptions()
      .setKeyStore(new KeyStoreOptions()
        .setType("jceks")
        .setPath("/home/hemant/Documents/vertx-starter/src/main/resources/keystore.jceks")
        .setPassword("secret"));

    JWTAuth jwt = JWTAuth.create(vertx, authConfig);

    router.post("/login").handler(routingContext ->
    {
      JsonObject json = routingContext.getBodyAsJson();

      if("admin@motadata.com".equals(json.getString("name")) && "admin".equals(json.getString("password")))
      {
        routingContext.request().response().end("Success");
      }

      else
      {
        routingContext.request().response().end("Failed");
      }
    });

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080);
  }

}
