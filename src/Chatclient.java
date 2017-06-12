import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatclient {
    static String name;
    static String password;
    Socket s;
    Clientdetails clientdetails = new Clientdetails();
    public void connect(){
        try{
            clientdetails.setClientName(name);
            s = new Socket("localhost",5000);
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
    public static void main(String args[]){
        System.out.println("Enter your Name please");
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine();
        System.out.println("Enter Password");
        password = scanner.nextLine();
        if (Authentication.isValidUser(name,password)){
            Chatclient chatclient = new Chatclient();
            chatclient.connect();
        }
        else{
            System.out.println("User Authentication Failed !!");
        }
    }
}
