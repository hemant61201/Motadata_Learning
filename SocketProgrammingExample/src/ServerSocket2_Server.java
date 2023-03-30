import java.net.*;
import java.io.*;

public class ServerSocket2_Server
{
    public static void main(String[] args)
    {
        try(ServerSocket serverSocket = new ServerSocket(3333); Socket socket = serverSocket.accept(); DataInputStream dataInputStream = new DataInputStream(socket.getInputStream()); DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream()); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
        {
            String clientMsg = "";

            Thread msgThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    String serverMSg = "";

                    try
                    {
                        while (!serverMSg.equals("stop"))
                        {
                            serverMSg = bufferedReader.readLine();

                            dataOutputStream.writeUTF(serverMSg);

                            dataOutputStream.flush();
                        }

                        System.exit(0);
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }

                }
            });

            msgThread.start();

            while (!clientMsg.equals("stop"))
            {
                clientMsg = dataInputStream.readUTF();

                System.out.println("Client Message : " + clientMsg);

                if (clientMsg.equals("stop"))
                {
                    System.exit(0);
                }
            }
//            dataOutputStream.writeUTF("stop");
//
//            dataOutputStream.flush();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
