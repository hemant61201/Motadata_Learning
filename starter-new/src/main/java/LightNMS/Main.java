package LightNMS;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main
{
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args)
  {

    Vertx vertx = Vertx.vertx();

    Promise<String> visualizationDeployment = Promise.promise();

    Promise<String> discoveryDeployment = Promise.promise();

    Promise<String> crudOperationsDeployment = Promise.promise();

    Promise<String> pollingExecutionDeployment = Promise.promise();

    vertx.deployVerticle(VisualPublicAPI.class.getName(), visualizationDeployment);

    vertx.deployVerticle(Discovery.class.getName(), discoveryDeployment);

    vertx.deployVerticle(CrudOperations.class.getName(), crudOperationsDeployment);

    vertx.deployVerticle(PollingExecution.class.getName(), pollingExecutionDeployment);

    CompositeFuture.all(visualizationDeployment.future(), discoveryDeployment.future(), crudOperationsDeployment.future(), pollingExecutionDeployment.future())
      .onComplete(ar ->
      {
        if (ar.succeeded())
        {
          logger.info("All verticles deployed successfully");
        }

        else
        {
          logger.info("One or more verticles failed to deploy");

          ar.cause().printStackTrace();

          System.exit(1);
        }
      });

      Runtime.getRuntime().addShutdownHook(new Thread(() ->
      {
        try
        {
          // Undeploy the verticles
          vertx.undeploy(visualizationDeployment.future().result());

          vertx.undeploy(discoveryDeployment.future().result());

          vertx.undeploy(crudOperationsDeployment.future().result());

          vertx.undeploy(pollingExecutionDeployment.future().result());
        }
        catch (Exception exception)
        {
          logger.error(String.valueOf(exception));
        }
      }));
  }
}
