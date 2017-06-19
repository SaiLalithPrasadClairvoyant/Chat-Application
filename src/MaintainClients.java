import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.slf4j.*;
/**
 * Created by Sai Lalith Pathi on 09-Jun-17.
 */
class MaintainClients implements Runnable{
    private static  Logger logger = LoggerFactory.getLogger(MaintainClients.class);
    private Socket socket;
    private User user;
    MaintainClients(User u){
        this.user = u;
        this.socket = u.getSocket();
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
                    for(User allUsers : Chatserver.getList(user)){
                        msgToClient(user.getUserName()+"  Left!", allUsers.getSocket());
                    }
                    socket.close();
                    break;
                } else {
                    Stats.add();
                    for(User allUsers : Chatserver.getList(user)) {
                        if(allUsers.getSocket() != socket) {
                            msgToClient(msgFromClient, allUsers.getSocket());
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Exception",e);

        }
    }
    private void msgToClient(String msgFromClient, Socket si) {
        try {
            PrintWriter pw = new PrintWriter(si.getOutputStream());
            pw.println(msgFromClient);
            pw.flush();
        } catch (IOException e) {
            logger.error("Exception",e);
        }
    }
}
