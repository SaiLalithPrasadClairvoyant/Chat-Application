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
    private static void startServer(){
        try {
            serverSocket = new ServerSocket(5000);
            logger.info("Server is running and waiting for clients.");
            while (true) {
                Socket s = serverSocket.accept();
                MaintainClients.msgToClient("Which group do you want to join?",s);
                InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String msg = bufferedReader.readLine();
                registerNewUser(msg,s);
                logger.info("Client Connected at port   " + s.getPort());
                if (allGroups.size() == 5) {
                    serverSocket.close();
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("Error while starting server {}",e);
        }
    }
   static List<User> getList(User u){
       for(Group g:allGroups){
           if(g.getUsers().contains(u)){
               return g.getUsers();
           }
       }
       return new ArrayList<>();
    }
    public static void main(String[] ar){
        TimerTask timerTask = new Stats();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask,0,30*1000L);
        Chatserver.startServer();
    }

    private static List<Group> getAllGroups() {
        return allGroups;
    }

    private static void addGroup(Group g){
       allGroups.add(g);
    }

    private static void registerNewUser(String groupNameFromUser,Socket s) throws IOException {
        MaintainClients.msgToClient("name please", s);
        InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String name = bufferedReader.readLine();
        User user = new User(name);
        MaintainClients.msgToClient("Password please", s);
        String password = bufferedReader.readLine();
        if (Authentication.isValidUser(name, password)) {
            ConnectionRegistry.setUserSocketMap(user, s);
            boolean groupExists = false;
            for (Group g : Chatserver.getAllGroups()) {
                if (groupNameFromUser.equalsIgnoreCase(g.getGroupName())) {
                    logger.info("User added to existing group !");
                    g.addUser(user);
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
            MaintainClients maintainClients = new MaintainClients(user);
            Thread maintainClient = new Thread(maintainClients);
            maintainClient.start();
        }
        else{
            MaintainClients.msgToClient("Authentication failed !",s);
        }
    }
}
