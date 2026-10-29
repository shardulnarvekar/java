package campus.lost.and.found.system;

public class User {
    String name;
    String location;
    String phone;
    String username;
    String password;
    String userType;

    public User(String name, String location, String phone, String username,
            String password, String userType) {
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }
}
