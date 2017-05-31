import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatserver {
    ArrayList<Socket> connectedClients;
    public void makeserver() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            Scanner scanner = new Scanner(System.in);
            while(true) {
                Socket s = serverSocket.accept();
                connectedClients.add(s);
                System.out.print("Client Connected");
                PrintWriter printWriter = new PrintWriter(s.getOutputStream());
                printWriter.print("Connected to the server at port"+s.getPort());
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String ar[]){
            new Chatserver().makeserver();
    }
}
