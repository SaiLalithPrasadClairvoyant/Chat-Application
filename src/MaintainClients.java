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
    private Socket s;
    private User user;
    MaintainClients(User u){
        this.user = u;
        this.s = u.getSocket();
    }
    @Override
    public void run() {
        try {
            logger.info(s.getLocalPort()+"MaintainThread");
            for(User all:Chatserver.getList(user)){
                System.out.println(all.getUserName());
            }
            InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String msgFromClient;
            while ((msgFromClient = bufferedReader.readLine()) != null) {
                if (msgFromClient.toLowerCase().contains("bye")) {
                    System.out.println("Client said BYE !!"+user.name);
                    for(User allUsers : Chatserver.getList(user)){
                        msgToClient(s.getPort()+"  Left!", allUsers.getSocket());
                    }
                    s.close();
                    break;
                } else {
                    Stats.add();
                    System.out.println(msgFromClient);
                    for(User allUsers : Chatserver.getList(user)) {
                        if(allUsers.getSocket() != user.getSocket()) {
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
