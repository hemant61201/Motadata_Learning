package com.example.vertx_starter.FutureOperations.Ping;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;
import java.util.function.Function;

public class PingImplementation extends AbstractVerticle{
  String finalResult;

  Promise<String> promise = Promise.promise();

  Scanner input = new Scanner(System.in);

  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
      Future<String> pingFuture = anAsyncAction();

      Future<Connection> connectionFuture = anotherAsyncAction();

      CompositeFuture.join(connectionFuture, pingFuture)
          .onComplete(ar -> {
            if (ar.succeeded())
            {
              try
              {
                PreparedStatement preparedStatement = connectionFuture.result().prepareStatement("insert into ips values(?)");

                System.out.println(pingFuture.result());

                String ip = pingFuture.result().split(" ")[0];

                preparedStatement.setString(1, ip);

                int rowUpdated = preparedStatement.executeUpdate();

                if(rowUpdated == 1)
                {
                  System.out.println("Ip inserted");
                }
                else
                {
                  System.out.println("Ip not inserted");
                }
              }

              catch (Exception e)
              {
                System.out.println("Connection Not Established");;
              }
            }
            else
            {
              System.out.println("One of the future is failed");
            }
          });

      startPromise.complete();
  }

  private Future<String> anAsyncAction()
  {
    try
    {
      System.out.println("enter ip or domain name");

      String message = input.next();

      String packetcmd = "ping -c 10" + " " + message + " | grep " + "'" + 10 + " " + "packets'";

      String[] cmd = {
        "/bin/sh",
        "-c",
        packetcmd
      };

      Process p = Runtime.getRuntime().exec(cmd);

      try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream())))
      {
        String packets = inputStream.readLine();

        try
        {
          String[] result;

          result = packets.split(",");

          System.out.println("Packet Loss : " + result[2]);

          String[] res = result[2].split(" ");

          String value = StringUtils.chop(res[1]);

          if(Integer.valueOf(value) > 60)
          {
            finalResult = message + " is Not Rechable.";
          }

          else
          {
            finalResult = message + " is Rechable.";
          }
        }

        catch (Exception exception)
        {
          exception.printStackTrace();
        }
      }

      catch (Exception exception)
      {
        exception.printStackTrace();
      }
    }

    catch (Exception exception)
    {
      exception.printStackTrace();
    }

    promise.complete(finalResult);

    return promise.future();
  }

  private Future<Connection> anotherAsyncAction() throws SQLException
  {
    try
    {
      Class.forName("org.h2.Driver");
    }

    catch (Exception exception)
    {
      System.out.println("Class Not Found");
    }

    Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test","sa","");

    Promise<Connection> promise1 = Promise.promise();

    promise1.complete(connection);

    return promise1.future();
  }
}
