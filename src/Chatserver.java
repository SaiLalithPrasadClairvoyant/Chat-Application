import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.*;
/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatserver {
    private static  Logger logger = LoggerFactory.getLogger(Chatserver.class);
    private static List<Group> allGroups = new ArrayList<>();
    private ServerSocket serverSocket = null;
    private void startServer(){
        try {
            serverSocket = new ServerSocket(5000);
            logger.info("Server is running and waiting for clients.");
            while (true) {
                Socket s = serverSocket.accept();
                logger.info("Client Connected at port   " + s.getPort());
                if (allGroups.size() == 5) {
                    serverSocket.close();
                    break;
                }
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
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
        Chatserver chatserver = new Chatserver();
        TimerTask timerTask = new Stats();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask,0,30*1000L);
        chatserver.startServer();
    }

    private static List<Group> getAllGroups() {
        return allGroups;
    }
    private static void addGroup(Group g){
       allGroups.add(g);
    }
    static void registerNewUser(String groupNameFromUser, User user) throws IOException {
        boolean groupExists = false;
        user.setSocket(new Socket("localhost",5000));
        for(Group g : Chatserver.getAllGroups()){
            if(groupNameFromUser.equalsIgnoreCase(g.getGroupName())){
                g.addUser(user);
                groupExists = true;
                break;
            }
        }
        if(!groupExists){
            Group newGroup = new Group();
            newGroup.setGroupName(groupNameFromUser);
            newGroup.addUser(user);
            Chatserver.addGroup(newGroup);
            logger.info("New group created");
        }
        MaintainClients maintainClients = new MaintainClients(user);
        Thread maintainClient = new Thread(maintainClients);
        maintainClient.start();
    }
}
