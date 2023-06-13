package LightNMS;

import io.vertx.core.*;

public class Main {

  public static void main(String[] args)
  {

    //TODO add Future Cordinations and also introduce Promise in all the verticles
    Vertx vertx = Vertx.vertx();

    Promise<String> visualizationDeployment = Promise.promise();

    Promise<String> discoveryDeployment = Promise.promise();

    Promise<String> crudOperationsDeployment = Promise.promise();

    Promise<String> pollingExecutionDeployment = Promise.promise();

    vertx.deployVerticle(Visualization.class.getName(), visualizationDeployment);

    vertx.deployVerticle(Discovery.class.getName(), discoveryDeployment);

    vertx.deployVerticle(CrudOperations.class.getName(), crudOperationsDeployment);

    vertx.deployVerticle(PollingExecution.class.getName(), pollingExecutionDeployment);

    CompositeFuture.all(visualizationDeployment.future(), discoveryDeployment.future(), crudOperationsDeployment.future(), pollingExecutionDeployment.future())
      .onComplete(ar ->
      {
        if (ar.succeeded())
        {
          System.out.println("All verticles deployed successfully");
        }

        else
        {
          System.out.println("One or more verticles failed to deploy");

          ar.cause().printStackTrace();

          System.exit(1);
        }
      });
  }
}
