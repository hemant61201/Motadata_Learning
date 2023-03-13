package org.example.March13;

class Sender
{
    public void SenderMsg(String msg)
    {
        System.out.println("\nSending a Message: "  + msg);

        try
        {
            Thread.sleep(800);
        }

        catch (Exception exception)
        {
            System.out.println("Thread interrupted.");
        }

        System.out.println("\n" +msg+ "Sent");
    }
}

class SenderWThreads extends Thread
{
    private final String msg;
    final Sender sender;

    SenderWThreads(String message, Sender obj)
    {
        msg = message;
        sender = obj;
    }

    public void run()
    {
        synchronized(sender)
        {
            sender.SenderMsg(msg);
        }
    }
}

public class SynchronizedBlock
{

    public static void main(String[] args)
    {
        Sender sender = new Sender();

        SenderWThreads sender1 = new SenderWThreads( "Hola " , sender);

        SenderWThreads sender2 =  new SenderWThreads( "Welcome to Javatpoint website ", sender);

        sender1.start();

        sender2.start();

        try
        {
            sender1.join();

            sender2.join();
        }

        catch(Exception exception)
        {
            System.out.println("Interrupted");
        }
    }
}
