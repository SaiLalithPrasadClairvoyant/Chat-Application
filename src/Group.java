import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sai Lalith Pathi on 15-Jun-17.
 */
class Group {
    private String groupName;
    private ArrayList<User> users = new ArrayList<>();
    List<User> getUsers() {
        return users;
    }
    void addUser(User u){
        this.users.add(u);
    }

    void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    String getGroupName() {
        return groupName;
    }
}
