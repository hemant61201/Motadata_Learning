package LightNMS;

import io.vertx.core.Vertx;

public class Main {

  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(Visualization.class.getName());

    vertx.deployVerticle(Discovery.class.getName());

    vertx.deployVerticle(QueryService.class.getName());
  }
}
