package ChatApplication;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println("Connected to server...");

        try(Socket clientSocket = new Socket("localhost", 4444))
        {
            System.out.println("Enter Your Name :");

            Scanner input = new Scanner(System.in);

            String name = input.nextLine();

            Thread readerThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    reader(name, clientSocket);
                }
            });

            readerThread.start();

            BufferedReader in = null;

            try
            {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String msg;

                while (true)
                {

                    msg = in.readLine();

                    if (msg.equals(null))
                    {
                        continue;
                    }

                    else
                    {
                        System.out.println(msg);
                    }
                }
            }

            catch (Exception exception)
            {
                in.close();

                exception.printStackTrace();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void reader(String name, Socket clientSocket)
    {
        System.out.println("View All Members --> '/all'");

        System.out.println("Create Group --> '/create {client1,client2} GroupName'");

        System.out.println("View All Groups --> '/groups'");

        System.out.println("Show Group Members --> '/members GroupName'");

        System.out.println("Join Group --> '/join groupName'");

        System.out.println("Leave Group --> '/leave groupName'");

        System.out.println("Send Message To Particular Group --> '/msg_groupName_{msg}");

        System.out.println("Send Private Message --> '/private clientName'");

        try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader input = new BufferedReader(new InputStreamReader(System.in)))
        {
            out.println(name);

            String userInput;

            while((userInput = input.readLine()) != null)
            {
                out.println(userInput);
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
