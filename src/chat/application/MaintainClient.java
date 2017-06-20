package chat.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.*;

class MaintainClient implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(MaintainClient.class);
    private Socket socket;
    private User user;

    MaintainClient(User u) {
        this.user = u;
        this.socket = ConnectionRegistry.getUserSocket(u);
    }

    static void msgToClient(String msgToSend, Socket si) {
        try {
            PrintWriter pw = new PrintWriter(si.getOutputStream());
            pw.println(msgToSend);
            pw.flush();
        } catch (IOException e) {
            logger.error("Exception", e);
        }
    }

    @Override
    public void run() {
        try {
            logger.info(socket.getLocalPort() + "MaintainThread");
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String msgFromClient;
            while ((msgFromClient = bufferedReader.readLine()) != null) {
                if (msgFromClient.equalsIgnoreCase("bye")) {
                    logger.info("left !" + user.getUserName());
                    for (User everyUser : Chatserver.getList(user)) {
                        msgToClient(user.getUserName() + "  Left!", ConnectionRegistry.getUserSocket(everyUser));
                    }
                    Chatserver.removeUser(user);
                    socket.close();
                    break;
                } else {
                    Stats.add();
                    for (User everyUser : Chatserver.getList(user)) {
                        if (!user.equals(everyUser)) {
                            msgToClient(user.getUserName() + ": " + msgFromClient, ConnectionRegistry.getUserSocket(everyUser));
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Exception", e);

        }
    }
}
