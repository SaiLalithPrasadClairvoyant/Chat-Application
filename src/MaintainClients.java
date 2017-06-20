import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.slf4j.*;

class MaintainClients implements Runnable{
    private static  Logger logger = LoggerFactory.getLogger(MaintainClients.class);
    private Socket socket;
    private User user;
    MaintainClients(User u){
        this.user = u;
        this.socket = ConnectionRegistry.getUserSocket(u);
    }
    @Override
    public void run() {
        try {
            logger.info(socket.getLocalPort()+"MaintainThread");
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String msgFromClient;
            while ((msgFromClient = bufferedReader.readLine()) != null) {
                if (msgFromClient.toLowerCase().contains("bye")) {
                    logger.info("left !"+user.getUserName());
                    for(User everyUser : Chatserver.getList(user)){
                        msgToClient(user.getUserName()+"  Left!", ConnectionRegistry.getUserSocket(everyUser));
                    }
                    socket.close();
                    break;
                } else {
                    Stats.add();
                    for(User everyUser : Chatserver.getList(user)) {
                        if(ConnectionRegistry.getUserSocket(everyUser) != socket) {
                            msgToClient(user.getUserName()+": "+msgFromClient, ConnectionRegistry.getUserSocket(everyUser));
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Exception",e);

        }
    }
    static void msgToClient(String msgFromClient, Socket si) {
        try {
            PrintWriter pw = new PrintWriter(si.getOutputStream());
            pw.println(msgFromClient);
            pw.flush();
        } catch (IOException e) {
            logger.error("Exception",e);
        }
    }
}
