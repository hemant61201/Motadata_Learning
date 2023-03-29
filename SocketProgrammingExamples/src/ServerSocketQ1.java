import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ServerSocketQ1
{
    public static void main(String[] args)
    {
        Thread serverThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try(ServerSocket serverSocket = new ServerSocket(3333); Socket socket = serverSocket.accept(); DataInputStream dataInputStream = new DataInputStream(socket.getInputStream()); DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream()); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
                {
                    String clientMsg = "";

                    String serverMsg = "";

                    while (!clientMsg.equals("stop"))
                    {
                        clientMsg = dataInputStream.readUTF();

                        System.out.println("Client Message : " + clientMsg);

                        if (clientMsg.equals("stop"))
                        {
                            System.exit(0);
                        }

                        serverMsg = clientMsg;

                        dataOutputStream.writeUTF("Message From Server : " + serverMsg);

                        dataOutputStream.flush();

                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        Thread clientThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String ip = "";

                System.out.println("Enter ip : ");

                Scanner input = new Scanner(System.in);

                ip = input.nextLine();

                System.out.println(ip);

                try
                {
                    Thread.sleep(30000);
                }
                catch (Exception e)
                {

                }

                try(Socket socket = new Socket(ip, 3333); DataInputStream dataInputStream = new DataInputStream(socket.getInputStream()); DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream()); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
                {
                    String clientMsg = "";

                    String serverMsg = "";

                    while (!serverMsg.equals("stop"))
                    {
                        System.out.println("Started sending message...");

                        clientMsg = bufferedReader.readLine();;

                        dataOutputStream.writeUTF(clientMsg);

                        dataOutputStream.flush();

                        serverMsg = dataInputStream.readUTF();

                        System.out.println(clientMsg);

                        if (serverMsg.equals("stop"))
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
        });

        serverThread.start();

        clientThread.start();
    }
}
