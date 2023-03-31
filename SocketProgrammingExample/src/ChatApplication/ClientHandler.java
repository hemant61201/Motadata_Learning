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
        BufferedWriter writer = null;

        try
        {
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            while (true)
            {
                String msg = in.readLine();

                if(msg.equals("/all"))
                {
                    all(writer);
                }

                else if (msg.contains("/create"))
                {
                    create(msg, writer);
                }

                else if (msg.equals("/groups"))
                {
                    groups(writer);
                }

                else if (msg.contains("/members"))
                {
                    members(msg, writer);
                }

                else if (msg.contains("/join"))
                {
                    join(msg, writer);
                }

                else if (msg.contains("/leave"))
                {
                    leave(msg, writer);
                }

                else if (msg.contains("/msg"))
                {
                    groupMsg(msg, writer);
                }

                else if (msg.contains("/private"))
                {
                    privateMsg(msg, writer);
                }

                else
                {
                    for (Socket member : clientSockets.values())
                    {
                        if (member == clientSocket)
                        {
                            continue;
                        }

                        PrintWriter writer1 = new PrintWriter(member.getOutputStream());

                        writer1.println(msg);

                        writer1.flush();
                    }
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

    public void all(BufferedWriter writer)
    {
        try
        {
            writer.write(String.valueOf(clients) + '\n');

            writer.flush();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void create(String msg, BufferedWriter writer)
    {
        try
        {
            String[] msgs = msg.split(" ");

            boolean flag = true;

            if(msgs.length == 3)
            {
                String[] clients = msgs[1].split(",");

                for (String client : clients)
                {
                    if (clientSockets.containsKey(client))
                    {
                        sockets.add(clientSockets.get(client));
                    }

                    else
                    {
                        writer.write("No Such Client in Server");

                        writer.flush();

                        flag = false;

                        break;
                    }
                }

                if(flag)
                {
                    group.put(msgs[2], sockets);

                    writer.write("Group " + msgs[2] + " is Created." + '\n');

                    writer.flush();
                }

                else
                {
                    writer.write("Please enter Correct Format --> '/create {client1,client2} GroupName'" + '\n');

                    writer.flush();
                }

            }

            else
            {
                writer.write("Please enter Correct Format --> '/create {client1,client2} GroupName'" + '\n');

                writer.flush();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void groups(BufferedWriter writer)
    {
        try
        {
            writer.write("Groups are : " + String.valueOf(group.keySet()) + '\n');

            writer.flush();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void members(String msg, BufferedWriter writer)
    {
        try
        {
            String[] msgs = msg.split(" ");

            if(msgs.length == 2)
            {
                if(group.containsKey(msgs[1]))
                {
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

                    writer.write("Members are : " + String.valueOf(members) + '\n');

                    writer.flush();
                }

                else
                {
                    writer.write("There is No Such Group" + '\n');

                    writer.flush();
                }
            }

            else
            {
                writer.write("Please enter Correct Format --> '/members GroupName'" + '\n');

                writer.flush();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void join(String msg, BufferedWriter writer)
    {
        try
        {
            String[] msgs = msg.split(" ");

            if(msgs.length == 2)
            {
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

            else
            {
                writer.write("Please enter Correct Format --> '/join groupName'" + '\n');

                writer.flush();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void leave(String msg, BufferedWriter writer)
    {
        try
        {
            String[] msgs = msg.split(" ");

            if(msgs.length == 2)
            {
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

            else
            {
                writer.write("Please enter Correct Format --> '/leave groupName'" + '\n');

                writer.flush();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void groupMsg(String msg, BufferedWriter writer)
    {
        try
        {
            String senderName = null;

            String[] msgs = msg.split("_");

            if(msgs.length == 3)
            {
                if (!group.containsKey(msgs[1]))
                {
                    writer.write("Group Does Not Exists" + '\n');

                    writer.flush();
                }

                for(Map.Entry<String, Socket> entry : clientSockets.entrySet())
                {
                    if(entry.getValue() == clientSocket)
                    {
                        senderName = entry.getKey();
                    }
                }

                ArrayList<Socket> groupMembers = group.get(msgs[1]);

                for(Socket groupMember: groupMembers)
                {
                    if (groupMember == clientSocket)
                    {
                        continue;
                    }

                    PrintWriter writer1 = new PrintWriter(groupMember.getOutputStream());

                    writer1.println(senderName + " : " + msgs[2]);

                    writer1.flush();
                }
            }

            else
            {
                writer.write("Please enter Correct Format --> '/msg_groupName_{msg}'" + '\n');

                writer.flush();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void privateMsg(String msg, BufferedWriter writer)
    {
        try
        {
            String senderName = null;

            String[] msgs = msg.split("_");

            if(msgs.length == 3)
            {
                for(Map.Entry<String, Socket> entry : clientSockets.entrySet())
                {
                    if(entry.getValue() == clientSocket)
                    {
                        senderName = entry.getKey();
                    }
                }

                if (!clientSockets.containsKey(msgs[1]))
                {
                    writer.write("Member Does Not Exists" + '\n');

                    writer.flush();
                }

                Socket person = clientSockets.get(msgs[1]);

                PrintWriter writer1 = new PrintWriter(person.getOutputStream());

                writer1.println(senderName + " [Private_Message] : " + msgs[2]);

                writer1.flush();
            }

            else
            {
                writer.write("Please enter Correct Format --> '/private_ClientName_{msg}'" + '\n');

                writer.flush();
            }
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
