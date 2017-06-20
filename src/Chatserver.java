import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.*;

public class Chatserver {
    private static final Logger logger = LoggerFactory.getLogger(Chatserver.class);
    private static List<Group> allGroups = new ArrayList<>();
    private static ServerSocket serverSocket = null;

    private static void startServer() {
        try {
            serverSocket = new ServerSocket(5000);
            logger.info("Server is running and waiting for clients.");
            while (true) {
                Socket s = serverSocket.accept();
                registerNewUser(s);
                logger.info("Client Connected at port   " + s.getPort());
                if (allGroups.size() == 5) {
                    serverSocket.close();
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("Error while starting server {}", e);
        }
    }

    static List<User> getList(User u) {
        for (Group g : allGroups) {
            if (g.getUsers().contains(u)) {
                return g.getUsers();
            }
        }
        return new ArrayList<>();
    }

    public static void main(String[] ar) {
        TimerTask timerTask = new Stats();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 30 * 1000L);
        Chatserver.startServer();
    }

    private static List<Group> getAllGroups() {
        return allGroups;
    }

    private static void addGroup(Group g) {
        allGroups.add(g);
    }

    private static void registerNewUser(Socket s) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        MaintainClient.msgToClient("name please", s);
        String name = bufferedReader.readLine();
        User user = new User(name);
        MaintainClient.msgToClient("Password please", s);
        String password = bufferedReader.readLine();
        MaintainClient.msgToClient("Which group do you want to join?", s);
        String groupNameFromUser = bufferedReader.readLine();
        if (Authentication.isValidUser(name, password)) {
            ConnectionRegistry.setUserSocketMap(user, s);
            boolean groupExists = false;
            for (Group g : Chatserver.getAllGroups()) {
                if (groupNameFromUser.equalsIgnoreCase(g.getGroupName())) {
                    logger.info("User added to existing group !");
                    g.addUser(user);
                    notifyAllClients(user);
                    groupExists = true;
                    break;
                }
            }
            if (!groupExists) {
                Group newGroup = new Group();
                newGroup.setGroupName(groupNameFromUser);
                newGroup.addUser(user);
                Chatserver.addGroup(newGroup);
                logger.info("New group created,Total groups :{}", Chatserver.getAllGroups().size());
            }
            MaintainClient maintainClient = new MaintainClient(user);
            Thread maintainClientThread = new Thread(maintainClient);
            maintainClientThread.start();
        } else {
            MaintainClient.msgToClient("Authentication failed !", s);
        }
    }

    private static void notifyAllClients(User user) {
        for (User everyUser : Chatserver.getList(user)) {
            if (!user.equals(everyUser)) {
                MaintainClient.msgToClient(user.getUserName() + " Joined..", ConnectionRegistry.getUserSocket(everyUser));
            }
        }
    }

    static void removeUser(User user){
        for(Group g : Chatserver.getAllGroups()){
            if(g.getUsers().contains(user)){
                g.removeUser(user);
            }
        }
    }
}
