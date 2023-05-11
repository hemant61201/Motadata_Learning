package com.example.vertx_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;


public class VerticleN extends AbstractVerticle
{
  public static void main(String[] args) {
    Vertx vertx1 = Vertx.vertx();
    vertx1.deployVerticle(new VerticleN());
  }
  @Override
  public void start(Promise<Void> startPromise) throws Exception
  {
    System.out.println("Start " + getClass().getName() + " on thread " + Thread.currentThread().getName() + " config " + config().toString());

      NetServer server = vertx.createNetServer();
      server.connectHandler(socket -> {
        System.out.println("connected");


        Buffer buffer = Buffer.buffer();
        buffer.appendString("hi").appendInt(2);
        socket.write(buffer).onComplete(voidAsyncResult -> {
          System.out.println("Sent message!");
        });
//        socket.handler(buffer -> {
//          // Just echo back the data
//          buffer.appendInt(1).appendInt(2);
//          socket.write(buffer);
//          System.out.println("data written");
//          socket.handler(res->{
//            System.out.println(res);
//          });
////          socket.write(buffer).onComplete(voidAsyncResult -> {
////            System.out.println("On success");
////          });
//        });
      });
      server.listen(1234,"10.20.40.156").onComplete(netServerAsyncResult -> {
        System.out.println("listening : "+netServerAsyncResult.result());
      });

      startPromise.complete();
    }
}
