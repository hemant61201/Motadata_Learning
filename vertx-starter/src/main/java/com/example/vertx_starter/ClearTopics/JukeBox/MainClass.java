package com.example.vertx_starter.ClearTopics.JukeBox;

import io.vertx.core.Vertx;

public class MainClass
{
  public static void main(String[] args)
  {
    Vertx vertex = Vertx.vertx();

    vertex.deployVerticle(Jukebox.class.getName());

    vertex.deployVerticle(NetControl.class.getName());
  }
}
