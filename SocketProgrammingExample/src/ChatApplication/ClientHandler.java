package ChatApplication;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientHandler extends Thread
{
    final ArrayList<String> clients;

    final HashMap<String, Socket> clientSockets;

    final Socket clientSocket;

    final BufferedReader in;

    final HashMap<String, ArrayList<Socket>> group;

    final ArrayList<Socket> sockets;

    public ClientHandler(Socket clientSocket, BufferedReader in, ArrayList<String> clients, HashMap<String, Socket> clientSockets, HashMap<String, ArrayList<Socket>> group, ArrayList<Socket> sockets)
    {
        this.clientSocket = clientSocket;

        this.clients = clients;

        this.in = in;

        this.clientSockets = clientSockets;

        this.group = group;

        this.sockets = sockets;
    }

    @Override
    public void run()
    {
//        while (true)
//        {
//
//        }
        BufferedWriter writer = null;

        try
        {
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            while (true)
            {
                String msg = in.readLine();

                if(msg.equals("/all"))
                {
                    writer.write(String.valueOf(clients) + '\n');

                    writer.flush();
                }

                else if (msg.contains("/create"))
                {
                    String[] msgs = msg.split(" ");

                    String[] clients = msgs[1].split(",");

                    for(String client : clients)
                    {
                        sockets.add(clientSockets.get(client));
                    }

                    group.put(msgs[2],sockets);

                    writer.write("Group " + msgs[2] + " is Created." + '\n');

                    writer.flush();
                }

                else if (msg.equals("/groups"))
                {
                    writer.write(String.valueOf(group.keySet()) + '\n');

                    writer.flush();
                }

                else if (msg.contains("/members"))
                {
                    String[] msgs = msg.split(" ");

                    ArrayList<Socket> groupSockets = group.get(msgs[1]);

                    ArrayList<String> members = new ArrayList<>();

                    for (Socket socket : groupSockets)
                    {
                        for(Map.Entry<String, Socket> entry : clientSockets.entrySet())
                        {
                            if(entry.getValue() == socket)
                            {
                                members.add(entry.getKey());
                            }
                        }
                    }

                    writer.write("Members are : " + String.valueOf(group) + '\n');

                    writer.flush();
                }

                else if (msg.contains("/join"))
                {
                    String[] msgs = msg.split(" ");

                    if (!group.containsKey(msgs[1]))
                    {
                        writer.write("Group Does Not Exists" + '\n');

                        writer.flush();
                    }

                    ArrayList<Socket> tempSocket = group.get(msgs[1]);

                    tempSocket.add(clientSocket);

                    group.put(msgs[1], tempSocket);

                    writer.write("Group Joined" + '\n');

                    writer.flush();
                }

                else if (msg.contains("/leave"))
                {
                    String[] msgs = msg.split(" ");

                    if (!group.containsKey(msgs[1]))
                    {
                        writer.write("You are Not in that Group" + '\n');

                        writer.flush();
                    }

                    ArrayList<Socket> tempSocket = group.get(msgs[1]);

                    tempSocket.remove(clientSocket);

                    group.put(msgs[1], tempSocket);

                    writer.write("You Leave The Group" + '\n');

                    writer.flush();
                }

                else if (msg.contains("/msg"))
                {
                    String[] msgs = msg.split("_");

                    if (!group.containsKey(msgs[1]))
                    {
                        writer.write("Group Does Not Exists" + '\n');

                        writer.flush();
                    }

                    ArrayList<Socket> groupMembers = group.get(msgs[1]);

                    for(Socket groupMember: groupMembers)
                    {
//                        if (groupMember == clientSocket)
//                        {
//                            continue;
//                        }
                        Thread writeThread = new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                BufferedWriter writer1 = null;

                                try
                                {
                                    writer1 = new BufferedWriter(new OutputStreamWriter(groupMember.getOutputStream()));

                                    writer1.write(msgs[2]);

                                    writer1.flush();
                                }

                                catch (Exception exception)
                                {
                                    exception.printStackTrace();
                                }
                            }
                        });

                        writeThread.start();
                    }
                }

                else
                {
                    continue;
                }
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            if(writer!= null)
            {
                try
                {
                    writer.close();
                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }
    }
}
