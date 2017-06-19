import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import org.slf4j.*;

/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatclient {
    private static Logger logger = LoggerFactory.getLogger(Chatclient.class);
    private static void connect(User u){
            ReadThread readThread = new ReadThread(u);
            Thread t1 = new Thread(readThread);
            t1.start();
            WriteThread writeThread = new WriteThread(u);
            Thread t2 = new Thread(writeThread);
            t2.start();
    }
    public void makeNewUser() throws IOException {
        System.out.println("Enter your Name please");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();
        System.out.println("Enter the group name");
        String group = scanner.nextLine();
        if (new Authentication().isValidUser(name, password)) {
            User user = new User(name);
            user.setSocket(new Socket("localhost",5000));
            Chatserver.registerNewUser(group, user);
            Chatclient.connect(user);
        } else {
            logger.info("User Authentication Failed !!");
        }
    }
    public static void main(String[] args) throws IOException {
        Chatclient chatclient = new Chatclient();
        chatclient.makeNewUser();
    }
}
