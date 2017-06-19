import java.net.Socket;

/**
 * Created by Sai Lalith Pathi on 15-Jun-17.
 */
class User {
    String name;

    String getUserName() {
        return name;
    }

    private Socket socket;

    User(String userName) {
        name = userName;
    }

    Socket getSocket() {
        return socket;
    }

    void setSocket(Socket socket) {
        this.socket = socket;
    }
}
