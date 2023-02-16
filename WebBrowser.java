package networkuser;

import java.io.IOException;

import java.net.URI;

import java.awt.*;

public interface WebBrowser {

    default void OpenWeb(String url, String rechable) throws IOException {

        if(rechable == "Host is reachable")
        {
            System.out.printf(String.valueOf(Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)));

            if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
            {
                try
                {
                    Desktop.getDesktop().browse(new URI("http://" + url));
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
        else
        {
            System.out.println("Can't open this website");
        }
    }
}
