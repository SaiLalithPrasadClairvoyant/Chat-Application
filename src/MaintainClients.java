import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Sai Lalith Pathi on 09-Jun-17.
 */
class MaintainClients implements Runnable{
    private Socket s;
    MaintainClients(Socket clientSocket){
        this.s = clientSocket;
    }
    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String msgFromClient;
            while ((msgFromClient = bufferedReader.readLine()) != null) {
                if (msgFromClient.toLowerCase().contains("bye")) {
                    System.out.println("Client said BYE !!"+s.getPort());
                    Chatserver.clients.remove(s);
                    for(Socket allClients : Chatserver.getList(s)){
                        msgToClient(s.getPort()+"  Left!",allClients);
                    }
                    s.close();
                    break;
                } else {
                    for(Socket allClients : Chatserver.getList(s)) {
                        if(allClients.getPort()!=s.getPort()) {
                            msgToClient(msgFromClient,allClients);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    static void msgToClient(String msgFromClient, Socket si) {
        try {
            PrintWriter pw = new PrintWriter(si.getOutputStream());
            pw.println(msgFromClient);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
