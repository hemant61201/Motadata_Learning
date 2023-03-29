import java.io.*;

import java.net.*;

public class ServerSocket1_Server
{
    public static void main(String[] args)
    {
        try(ServerSocket serverSocket = new ServerSocket(6666); Socket socket = serverSocket.accept(); DataInputStream dataInputStream = new DataInputStream(socket.getInputStream()))
        {
            String msg = dataInputStream.readUTF();

            System.out.println("Message Server : " + msg);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
