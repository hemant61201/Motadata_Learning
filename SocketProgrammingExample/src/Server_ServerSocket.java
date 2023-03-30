import java.io.*;
import java.net.*;
public class Server_ServerSocket
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(4444);

            System.out.println("Server listening on port 4444...");

            Socket clientSocket = serverSocket.accept();

            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                System.out.println("Client says: " + inputLine);

                out.println("Server received: " + inputLine);
            }

            in.close();

            out.close();

            clientSocket.close();

            serverSocket.close();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}