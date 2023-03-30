import java.io.*;
import java.net.*;

public class GroupChat_Server
{
    public static void main(String[] args)
    {
        BufferedReader in = null;

        System.out.println("Server listening on port 4444...");

        try(ServerSocket serverSocket = new ServerSocket(4444); Socket clientSocket = serverSocket.accept())
        {
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                System.out.println("Client says: " + inputLine);

                if(inputLine.equals("stop"))
                {
                    System.exit(0);
                }
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }
    }
}
