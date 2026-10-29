package campus.lost.and.found.web;

public class User {
    public String name;
    public String location;
    public String phone;
    public String username;
    public String password;
    public String userType;

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
