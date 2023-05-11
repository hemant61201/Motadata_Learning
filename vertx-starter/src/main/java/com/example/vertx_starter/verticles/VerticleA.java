package com.example.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

public class VerticleA extends AbstractVerticle
{
  public static void main(String[] args) {
    Vertx vertx1 = Vertx.vertx();
    vertx1.deployVerticle(new VerticleA());
  }
    @Override
    public void start(Promise<Void> startPromise) throws Exception
    {
        System.out.println("Start " + getClass().getName());

//        vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
//
//          System.out.println("Deployed" + VerticleAA.class.getName());
//
//          vertx.undeploy(whenDeployed.result());
//        });
//
//        vertx.deployVerticle(new VerticleAB(), whenDeployed -> {
//
//          System.out.println("Deployed" + VerticleAA.class.getName());
//        });

        NetClientOptions options = new NetClientOptions().setConnectTimeout(10000);
        NetClient client = vertx.createNetClient(options);
        client
          .connect(1234, "10.20.40.156")
          .onComplete(res -> {
            if (res.succeeded())
            {
              System.out.println("Connected!");
              NetSocket socket = res.result();
              socket.handler(buffer ->
              {
                System.out.println("message received");
                System.out.println(buffer.getString(0,2));
                System.out.println(buffer.getInt(3));
              });
            }
            else {
              System.out.println("Failed to connect: " + res.cause().getMessage());
            }
          });

        startPromise.complete();
    }
}
