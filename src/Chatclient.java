import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatclient {
    static String name;
    public void connect(){
        try{
            System.out.println("Enter your name please");
            Scanner scanner = new Scanner(System.in);
            name = scanner.next();
            //System.out.print(name);
            Socket s = new Socket("localhost",5000);
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
    public class ReadThread implements Runnable{
        Socket s;
        public ReadThread(Socket clientSocket){
            this.s = clientSocket;
        }
        @Override
        public void run() {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String msg;
                while(true){
                    while((msg = bufferedReader.readLine())!= null){
                        System.out.println(msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public class WriteThread implements Runnable{
        Socket s;
        String name;
        public WriteThread(Socket clientSocket,String name){
            this.s = clientSocket;
            this.name = name;
        }
        @Override
        public void run() {
            try {
                PrintWriter pw = new PrintWriter(s.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String msg;
                while(true){
                    msg = bufferedReader.readLine();

                    pw.println(name+": "+msg);
                    pw.flush();
                    if(msg.toLowerCase().equals("bye")) {
                        System.exit(0);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String args[]){
        new Chatclient().connect();
    }
}
