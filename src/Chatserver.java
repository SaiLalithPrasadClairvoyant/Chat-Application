import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatserver {
    private ServerSocket serverSocket = null;
    static ArrayList<Socket> clients = new ArrayList<>();
    private static ArrayList<Socket> one = new ArrayList<>();
    private void makeserver()  {
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server is running and waiting for clients.");
            while (true) {
                Socket s = serverSocket.accept();
                System.out.println("Client Connected at port   " + s.getPort());
                MaintainClients.msgToClient("Which Group", s);
                InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String group = bufferedReader.readLine();
                if (group.contains("one")) {
                    System.out.println("Member added to One group");
                    for (Socket allClients : one) {
                        MaintainClients.msgToClient("New client joined to group ONE!", allClients);
                    }
                    one.add(s);
                } else {
                    for (Socket allClients : clients) {
                        MaintainClients.msgToClient("New client joined !", allClients);
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
    public static void main(String[] ar){
        TimerTask timerTask = new Stats();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask,0,30*1000L);
        new Chatserver().makeserver();
    }
}
