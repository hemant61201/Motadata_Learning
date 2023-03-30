import java.net.*;
import java.io.*;
public class ServerSocket2_Client
{
    public static void main(String[] args)
    {
        try(Socket socket = new Socket("localhost", 3333); DataInputStream dataInputStream = new DataInputStream(socket.getInputStream()); DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream()); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
        {

//            socket.isBound();

            String serverMSg = "";

            Thread msgThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    String clientMSg = "";

                    try
                    {
                        while (!clientMSg.equals("stop"))
                        {
                            clientMSg = bufferedReader.readLine();

                            dataOutputStream.writeUTF(clientMSg);

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

            while (!serverMSg.equals("stop"))
            {
                serverMSg = dataInputStream.readUTF();

                System.out.println("Server Message : " + serverMSg);

                if (serverMSg.equals("stop"))
                {
                    System.exit(0);
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}