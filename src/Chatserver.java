import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatserver {
    static int totalMessages=0;
    static ArrayList<Socket> clients = new ArrayList<>();
    static ArrayList<Socket> one = new ArrayList<>();
    void makeserver(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running and waiting for clients.");
            while(true) {
                Socket s = serverSocket.accept();
                System.out.println("Client Connected at port   " + s.getPort());
                MaintainClients.msgToClient("Which Group",s);
                InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String group = bufferedReader.readLine();
                if(group.contains("one")){
                    System.out.println("Member added to One group");
                    one.add(s);
                }else {
                    for (Socket allClients : clients) {
                        MaintainClients.msgToClient("Welcome new Client at " + s.getPort(), allClients);
                    }
                    clients.add(s);
                }
                MaintainClients maintainClients = new MaintainClients(s);
                Thread t1 = new Thread(maintainClients);
                t1.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static ArrayList<Socket> getList(Socket s){
        if(one.contains(s))
        return one;
        else
        return clients;
    }
    static void moreMessages(){
        totalMessages++;
    }
    /*static void addClient(Chatclient cc){
        chatclients.add(cc);
    }*/
    public static void main(String ar[]){
        new Chatserver().makeserver(5000);
    }
}
