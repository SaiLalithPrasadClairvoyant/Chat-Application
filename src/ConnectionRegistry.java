import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


class ConnectionRegistry{
    private ConnectionRegistry(){
        throw new IllegalStateException("Utility class");
    }
    private static Map<User,Socket> userSocketMap = new HashMap<>();
    static Socket getUserSocket(User user){
        for(Map.Entry<User,Socket> userSocketEntry : userSocketMap.entrySet()){
            if(userSocketEntry.getKey().equals(user)){
                return userSocketEntry.getValue();
            }
        }
        return null;
    }
    static void setUserSocketMap(User user,Socket socket){
        userSocketMap.put(user,socket);
    }
}
