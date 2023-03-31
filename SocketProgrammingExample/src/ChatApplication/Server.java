package ChatApplication;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Server
{
    public static void main(String[] args)
    {
        System.out.println("Server listening on port 4444...");

        try
        {
            ServerSocket serverSocket = new ServerSocket(4444);

            ArrayList<String> clients = new ArrayList<>();

            HashMap<String, Socket> clientSockets = new HashMap<>();

            HashMap<String, ArrayList<Socket>> group = new HashMap<>();

            ArrayList<Socket> sockets = new ArrayList<>();

            while (true)
            {
                Socket clientSocket = null;

                BufferedReader in = null;

                try
                {
                    clientSocket = serverSocket.accept();

                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String clientName = in.readLine();

                    clients.add(clientName);

                    clientSockets.put(clientName, clientSocket);

                    Thread clientHandlerThread = new ClientHandler(clientSocket, in, clients, clientSockets, group, sockets);

                    clientHandlerThread.start();
                }

                catch (Exception exception)
                {
                    clientSocket.close();

                    exception.printStackTrace();
                }
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
