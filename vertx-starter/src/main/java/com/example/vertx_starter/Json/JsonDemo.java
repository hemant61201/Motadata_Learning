package com.example.vertx_starter.Json;

import io.vertx.core.json.JsonObject;

public class JsonDemo
{
  public static void main(String[] args)
  {
    String json = "{\"FirstName\" : \"Hemant\", \"LastName\" : \"Vadhaiya\", \"Company\" : \"Motadata\"}";

    System.out.println(json);

    JsonObject jsonObject = new JsonObject(json);

    System.out.println(jsonObject.getString("FirstName"));

    System.out.println(jsonObject.getString("LastName"));

    System.out.println();
  }
}
