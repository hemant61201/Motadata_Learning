import java.io.*;
import java.net.*;

public class GroupChat_Client1
{
    public static void main(String[] args)
    {
        PrintWriter out = null;

        BufferedReader input = null;

        try(Socket clientSocket = new Socket("localhost", 4444);)
        {
            System.out.println("Connected to server...");

            out = new PrintWriter(clientSocket.getOutputStream(), true);

            input = new BufferedReader(new InputStreamReader(System.in));

            String userInput;

            while((userInput = input.readLine()) != null)
            {
                out.println(userInput);

                if (userInput.equals("stop"))
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
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }

            if(input != null)
            {
                try
                {
                    input.close();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }
    }
}
