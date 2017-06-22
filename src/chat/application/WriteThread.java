package chat.application;

import java.io.*;
import java.net.Socket;

import org.slf4j.*;

public class WriteThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(WriteThread.class);
    private Socket s;

    WriteThread(Socket s) {
        this.s = s;
    }

    static char[] askForPassword() {
        char[] dum = {'a', 'b'};
        Console console = System.console();
        if (console != null) {
            return console.readPassword();
        }
        return dum;
    }

    @Override
    public void run() {
        try {
            logger.info(s.getLocalPort() + "WriteThread");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String msg;
            while (true) {
                msg = bufferedReader.readLine();
                pw.println(msg);
                pw.flush();
                if (msg.equalsIgnoreCase("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("IOException at WriteThread", e);
            System.exit(0);
        }
    }
}
