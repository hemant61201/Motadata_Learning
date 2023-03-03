package networkadmin;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.io.InputStream;

import com.jcraft.jsch.*;

public interface SshImplementationAdmin extends RunCommandImplementation {

    default void sshImplemntation()
    {
        System.out.println("Welcome to SSH Functionality");

        System.out.println("Please Enter valid Inputs");

        try(BufferedReader input = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.println("Enter Host Address");

            String host = input.readLine();

            System.out.println("Enter User Name");

            String user = input.readLine();

            System.out.println("Enter Password");

            String passwd = input.readLine();

            try
            {
                java.util.Properties config = new java.util.Properties();

                config.put("StrictHostKeyChecking", "no");

                JSch jsch = new JSch();

                Session session=jsch.getSession(user, host, 22);

                session.setPassword(passwd);

                session.setConfig(config);

                session.connect();

                System.out.println("Connected");

                System.out.println("Want To Run Command Enter : Y/N");

                char ans = input.readLine().charAt(0);

                if(ans == 'Y' || ans == 'y')
                {
                    String command = runCommand();

                    Channel channel=session.openChannel("exec");

                    ((ChannelExec)channel).setCommand(command);

                    channel.setInputStream(null);

                    ((ChannelExec)channel).setErrStream(System.err);

                    InputStream in=channel.getInputStream();

                    channel.connect();

                    byte[] tmp=new byte[1024];

                    while(true)
                    {
                        while(in.available()>0)
                        {
                            int i=in.read(tmp, 0, 1024);

                            if(i<0)
                            {
                                break;
                            }

                            System.out.print(new String(tmp, 0, i));
                        }

                        if(channel.isClosed())
                        {
                            System.out.println("exit-status: "+channel.getExitStatus());

                            break;
                        }

                        try
                        {
                            Thread.sleep(1000);
                        }

                        catch(Exception exception)
                        {
                            exception.printStackTrace();
                        }
                    }

                    channel.disconnect();

                    session.disconnect();

                    System.out.println("DONE");
                }

                else
                {
                    session.disconnect();
                }

            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}
