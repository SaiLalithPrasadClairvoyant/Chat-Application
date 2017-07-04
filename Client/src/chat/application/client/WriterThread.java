package chat.application.client;

import java.io.*;
import java.net.Socket;

import org.slf4j.*;

public class WriterThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(WriterThread.class);
    private Socket s;

    WriterThread(Socket s) {
        this.s = s;
    }

    private static char[] askForPassword() {
        char[] dummy = {'a', 'b'};
        Console console = System.console();
        if (console != null) {
            return console.readPassword();
        }
        else{
            logger.info("Cannot find console .. Exiting !");
            System.exit(0);
        }
        return dummy;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            logger.info(s.getLocalPort() + "WriteThread");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String msg = "";
            while (true) {
                if (i == 1) {
                    String pass = new String(askForPassword());
                    pw.println(pass);
                    pw.flush();
                    i = 10;
                } else {
                    msg = bufferedReader.readLine();
                    pw.println(msg);
                    pw.flush();
                    i++;
                }
                if (msg.equalsIgnoreCase("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("IOException at WriteThread", e);
        }
    }
}
