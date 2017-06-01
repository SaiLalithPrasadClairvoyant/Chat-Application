import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatclient {
    public void connect(){
        try{
            Socket s = new Socket("localhost",5000);
            ReadThread readThread = new ReadThread(s);
            Thread t1 = new Thread(readThread);
            t1.start();
            WriteThread writeThread = new WriteThread(s);
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
                        if(msg.equals("Bye")){
                            System.out.println("Server said BYE !!");
                            System.exit(0);
                        }else{
                            System.out.println(msg);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public class WriteThread implements Runnable{
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
    public static void main(String args[]){
        new Chatclient().connect();
    }
}
