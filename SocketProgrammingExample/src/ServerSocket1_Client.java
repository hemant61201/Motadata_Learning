import java.io.*;

import java.net.*;

public class ServerSocket1_Client
{
    public static void main(String[] args)
    {
        try(Socket socket = new Socket("localhost", 6666); DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream()))
        {
            dataOutputStream.writeUTF("Hello Hemant");

            dataOutputStream.flush();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
