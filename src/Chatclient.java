import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatclient {
    private static String name;
    private static Chatclient chatclient = new Chatclient();
    private Clientdetails clientdetails = new Clientdetails();
    private void connect(){
        try{
            clientdetails.setClientName(name);
            Socket s = new Socket("localhost", 5000);
            ReadThread readThread = new ReadThread(s);
            Thread t1 = new Thread(readThread);
            t1.start();
            WriteThread writeThread = new WriteThread(s,name);
            Thread t2 = new Thread(writeThread);
            t2.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        System.out.println("Enter your Name please");
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();
        if (Authentication.isValidUser(name, password)){
            chatclient.connect();
        }
        else{
            System.out.println("User Authentication Failed !!");
        }
    }
}
