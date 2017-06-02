import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatserver {
    public static ArrayList<Socket> clients = new ArrayList<>();
    public void makeserver() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server is running and waiting for clients.");
            while(true) {
                Socket s = serverSocket.accept();
                System.out.println("Client Connected at port   " + s.getPort());
                for(Socket allSockets:clients){
                    msgToClient("Welcome new Client at "+s.getPort(),allSockets);
                }
                clients.add(s);
                MaintainClients readThread = new MaintainClients(s);
                Thread t1 = new Thread(readThread);
                t1.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String ar[]){
            new Chatserver().makeserver();
    }
    class MaintainClients implements Runnable{
        Socket s;
        public MaintainClients(Socket clientSocket){
            this.s = clientSocket;
        }
        @Override
        public void run() {
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String msgFromClient;
                        while ((msgFromClient = bufferedReader.readLine()) != null) {
                            if (msgFromClient.toLowerCase().equals("bye")) {
                                System.out.println("Client said BYE !!"+s.getPort());
                                clients.remove(s);
                                for(Socket sii : clients){
                                    msgToClient(s.getPort()+"  Left!",sii);
                                }
                                s.close();
                                break;
                            } else {
                                for(Socket si : clients) {
                                    if(si.getPort()!=s.getPort()) {
                                        msgToClient(msgFromClient, si);
                                    }
                                }
                            }
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    public void msgToClient(String msgFromClient, Socket si) {
        try {
            PrintWriter pw = new PrintWriter(si.getOutputStream());
            pw.println(msgFromClient);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
