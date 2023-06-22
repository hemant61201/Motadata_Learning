package LightNMS;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main
{
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args)
  {
    try
    {
      Vertx vertx = Vertx.vertx();

      Promise<String> visualizationDeployment = Promise.promise();

      Promise<String> discoveryDeployment = Promise.promise();

      Promise<String> crudOperationsDeployment = Promise.promise();

      Promise<String> pollingExecutionDeployment = Promise.promise();

      vertx.deployVerticle(VisualPublicAPI.class.getName(), visualizationDeployment);

      vertx.deployVerticle(Discovery.class.getName(), new DeploymentOptions().setWorkerPoolName("discoveryPool").setWorkerPoolSize(10), discoveryDeployment);

      vertx.deployVerticle(DatabaseOperations.class.getName(), crudOperationsDeployment);

      vertx.deployVerticle(PollingExecution.class.getName(), pollingExecutionDeployment);

      CompositeFuture.all(visualizationDeployment.future(), discoveryDeployment.future(), crudOperationsDeployment.future(), pollingExecutionDeployment.future())
        .onComplete(ar ->
        {
          if (ar.succeeded())
          {
            LOGGER.info("All verticles deployed successfully");
          }

          else
          {
            LOGGER.info("One or more verticles failed to deploy");

            ar.cause().printStackTrace();

            System.exit(1);
          }
        });

      Runtime.getRuntime().addShutdownHook(new Thread(() ->
      {
        try
        {
          // Undeploy the verticles
          vertx.undeploy(VisualPublicAPI.class.getName());

          vertx.undeploy(Discovery.class.getName());

          vertx.undeploy(DatabaseOperations.class.getName());

          vertx.undeploy(PollingExecution.class.getName());

          LOGGER.info("All Verticles Successfully Undeploy");
        }
        catch (Exception exception)
        {
          LOGGER.error(String.valueOf(exception));
        }
      }));
    }

    catch (Exception exception)
    {
      LOGGER.error(exception.getMessage());
    }
  }
}
