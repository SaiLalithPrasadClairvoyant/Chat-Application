import java.util.ArrayList;
import java.util.List;


class Group {
    private String groupName;
    private List<User> users = new ArrayList<>();

    List<User> getUsers() {
        return users;
    }

    void removeUser(User user) {
        this.users.remove(user);
    }

    void addUser(User u) {
        this.users.add(u);
    }

    String getGroupName() {
        return groupName;
    }

    void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
