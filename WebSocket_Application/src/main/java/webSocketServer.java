import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{username}")
public class webSocketServer {

    private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    @OnMessage
    public String onMessage(Session session, String message)
    {
        String[] messages = message.split("_");

        String clientName = messages[1];

        System.out.println("handling message: " + message);

        try
        {
            Session client = sessions.get(clientName);

            String name = null;

            for(Map.Entry<String, Session> entry : sessions.entrySet())
            {
                if(entry.getValue() == client)
                {
                    name = entry.getKey();
                }
            }

            client.getBasicRemote().sendText(name + " Send : " + messages[0]);

//            session.setMaxIdleTimeout(60000);

        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
//
//        for (Session session1 : sessions.keySet()){
//
////            if(session1 == session)
////            {
////                continue;
////            }
//
//            try {
//                session1.getBasicRemote().sendText(message + " from " + sessions.get(session1));
//
////                session1.setMaxIdleTimeout(60000);
//
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
//        }
        return null;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String userName) throws IOException
    {
        System.out.println("On open: "+ session.getId());

        session.setMaxIdleTimeout(30000);

        System.out.println("Name " + userName);

        sessions.put(userName, session);

        for(Session session1: sessions.values())
        {
            session1.getBasicRemote().sendText("Clients : " + sessions.keySet());
        }
    }

    @OnClose
    public void onClose(Session session)
    {
        System.out.println("On close: "+ session.getId());

        sessions.remove(sessions.get(session));
    }

}