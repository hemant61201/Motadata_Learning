package networkadmin;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;

import java.io.InputStream;

public interface RunCommandImplementation {

    default String runCommand()
    {
        String s = null;
        try
        {
            System.out.println("Command List :");

            System.out.println("1. Print input ");

            s = "ping google.com";
            return s;
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return s;
    }
}
