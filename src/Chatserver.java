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
            while(true) {
                Socket s = serverSocket.accept();
                System.out.print("Client Connected at port" + s.getPort());
                //clients.add(s);
                ReadThread readThread = new ReadThread(s);
                Thread t1 = new Thread(readThread);
                t1.start();
                WriteThread writeThread = new WriteThread(s);
                Thread t2 = new Thread(writeThread);
                t2.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String ar[]){
            new Chatserver().makeserver();
    }
    class ReadThread implements Runnable{
        Socket s;
        public ReadThread(Socket clientSocket){
            this.s = clientSocket;
        }
        @Override
        public void run() {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String msgFromClient;
                while(true){
                    while((msgFromClient = bufferedReader.readLine())!=null){
                        if(msgFromClient.equals("Bye")){
                            System.out.println("Client said BYE !!");
                            System.exit(0);
                        }else{
                         System.out.println(msgFromClient);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    class WriteThread implements Runnable{
        Socket s;
        public WriteThread(Socket clientSocket){
            this.s = clientSocket;
        }
        @Override
        public void run() {
            try {
                PrintWriter pw = new PrintWriter(s.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String msg;
                while(true){
                    msg = bufferedReader.readLine();
                    pw.println(msg);
                    pw.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
   /* public class Maintain implements Runnable{
        Socket s ;
        public Maintain(Socket s){
            this.s = s;
        }
        @Override
        public void run() {
            ReadThread readThread= new ReadThread(s);
            Thread t1 = new Thread(readThread);
            t1.start();
            WriteThread writeThread = new WriteThread(s);
            Thread t2 = new Thread(writeThread);
            t2.start();
        }
    }*/
}
