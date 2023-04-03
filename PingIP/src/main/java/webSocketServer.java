import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
public class webSocketServer {

    private static ConcurrentHashMap<String, Session> ipSessions = new ConcurrentHashMap<>();

    private static Set<Session> sessions = new HashSet<>();

    @OnMessage
    public String onMessage(Session session, String message)
    {

        System.out.println("handling message: " + message);

        try
        {
            ipSessions.put(message,session);

            session.getBasicRemote().sendText("Start Pinging :" + message);

            Thread pingThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        String packetcmd = "ping -c 10" + " " + message + " | grep " + "'" + 10 + " " + "packets'";

                        String[] cmd = {
                                "/bin/sh",
                                "-c",
                                packetcmd
                        };

                        Process p = Runtime.getRuntime().exec(cmd);

                        try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream())))
                        {
                            String packets = inputStream.readLine();

                            try
                            {
                                String[] result;

                                result = packets.split(",");

                                System.out.println("Packet Loss : " + result[2]);

                                String[] res = result[2].split(" ");

                                System.out.println(StringUtils.chop(res[1]));

                                String value = StringUtils.chop(res[1]);

                                if(Integer.valueOf(value) > 60)
                                {
                                    ipSessions.get(message).getBasicRemote().sendText(message + " is Not Rechable.");

                                }

                                else
                                {
                                    ipSessions.get(message).getBasicRemote().sendText(message + " is Rechable.");
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

                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            });

            pingThread.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @OnOpen
    public void onOpen(Session session)
    {
        System.out.println("On open: "+ session.getId());

        session.setMaxIdleTimeout(5 * 60000);

        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session)
    {
        System.out.println("On close: "+ session.getId());

        sessions.remove(session);
    }

}