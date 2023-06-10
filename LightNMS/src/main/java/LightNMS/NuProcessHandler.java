package LightNMS;

import com.zaxxer.nuprocess.NuAbstractProcessHandler;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

public class NuProcessHandler extends NuAbstractProcessHandler
{
  private final CompletableFuture<String> processOutputFuture;
  private final StringBuilder outputBuffer;

  public NuProcessHandler(CompletableFuture<String> processOutputFuture)
  {
    this.processOutputFuture = processOutputFuture;

    this.outputBuffer = new StringBuilder();
  }

  @Override
  public void onStdout(ByteBuffer buffer, boolean closed)
  {
    byte[] bytes = new byte[buffer.remaining()];

    buffer.get(bytes);

    outputBuffer.append(new String(bytes));
  }

  @Override
  public void onStderr(ByteBuffer buffer, boolean closed)
  {
    byte[] bytes = new byte[buffer.remaining()];

    buffer.get(bytes);

    outputBuffer.append(new String(bytes));
  }

  @Override
  public void onExit(int statusCode)
  {
    processOutputFuture.complete(outputBuffer.toString());
  }
}
