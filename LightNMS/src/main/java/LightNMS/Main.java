package LightNMS;

import io.vertx.core.Vertx;

public class Main {

  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(Visualization.class.getName()).onSuccess(res -> {
      System.out.println("deploy succede visual" + res);
    });

    vertx.deployVerticle(Discovery.class.getName()).onSuccess(res -> {
      System.out.println("deploy succede discovery" + res);
    });

    vertx.deployVerticle(CrudOperations.class.getName()).onSuccess(res -> {
      System.out.println("deploy succede crud" + res);
    });

    vertx.deployVerticle(PollingExecution.class.getName()).onSuccess(res -> {
      System.out.println("deploy succede crud" + res);
    });
  }
}
