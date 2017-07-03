package chat.application.server;

class User {
    private String name;

    User(String userName) {
        name = userName;
    }

    String getUserName() {
        return name;
    }

}
