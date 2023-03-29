import java.io.*;
import java.net.*;

public class ClientServerSocket
{
    public static void main(String[] args) throws IOException
    {
        Socket clientSocket = new Socket("localhost", 4444);

        System.out.println("Connected to server...");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String userInput;

        while ((userInput = stdIn.readLine()) != null)
        {
            out.println(userInput);

            System.out.println("Server says: " + in.readLine());
        }

        out.close();

        in.close();

        stdIn.close();

        clientSocket.close();
    }
}

