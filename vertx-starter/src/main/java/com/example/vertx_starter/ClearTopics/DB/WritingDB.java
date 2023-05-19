package com.example.vertx_starter.ClearTopics.DB;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;

public class WritingDB extends AbstractVerticle
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(WritingDB.class.getName());
  }

  @Override
  public void start() throws Exception
  {
    AsyncFile file = vertx.fileSystem().openBlocking("sample.db", new OpenOptions().setWrite(true).setCreate(true));

    Buffer buffer = Buffer.buffer();

    buffer.appendBytes(new byte[] { 1, 2, 3, 4});

    buffer.appendInt(2);

    buffer.appendString("Sample database\n");

    String key = "abc";

    String value = "123456-abcdef";

    buffer
      .appendInt(key.length())
      .appendString(key)
      .appendInt(value.length())
      .appendString(value);

    key = "foo@bar";

    value = "Foo Bar Baz";

    buffer
      .appendInt(key.length())
      .appendString(key)
      .appendInt(value.length())
      .appendString(value);

    file.end(buffer, ar -> vertx.close());
  }
}
