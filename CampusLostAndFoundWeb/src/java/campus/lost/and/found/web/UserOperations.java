package campus.lost.and.found.web;

import java.sql.*;

public class UserOperations extends User {
    public UserOperations(String name, String location, String phone,
            String username, String password, String userType) {
        super(name, location, phone, username, password, userType);
    }

    public void registerUser() throws SQLException {
        if (name.isEmpty() || location.isEmpty() || phone.isEmpty()
                || username.isEmpty() || password.isEmpty()) {
            throw new SQLException("Complete all registration fields.");
        }
        try (Connection con = DBConnect.dbCon()) {
            String sql = "SELECT id FROM users WHERE username=? OR name=?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, username);
                pst.setString(2, name);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        throw new SQLException("This name or student ID / username is already registered.");
                    }
                }
            }
            sql = "INSERT INTO users (name,location,phone,username,password,usertype) VALUES (?,?,?,?,?,'USER')";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, name);
                pst.setString(2, location);
                pst.setString(3, phone);
                pst.setString(4, username);
                pst.setString(5, password);
                pst.executeUpdate();
            }
        }
    }

    public static UserOperations login(String username, String password, String role) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=? AND password=? AND usertype=?";
        try (Connection con = DBConnect.dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return readUser(rs);
            }
        }
        return null;
    }

    public static UserOperations searchUser(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection con = DBConnect.dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return readUser(rs);
            }
        }
        return null;
    }

    private static UserOperations readUser(ResultSet rs) throws SQLException {
        return new UserOperations(rs.getString("name"), rs.getString("location"),
                rs.getString("phone"), rs.getString("username"),
                rs.getString("password"), rs.getString("usertype"));
    }

    public static boolean changePassword(String username, String oldPassword,
            String newPassword) throws SQLException {
        if (newPassword.isEmpty()) throw new SQLException("Enter a new password.");
        String sql = "UPDATE users SET password=? WHERE username=? AND password=?";
        try (Connection con = DBConnect.dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, newPassword);
            pst.setString(2, username);
            pst.setString(3, oldPassword);
            return pst.executeUpdate() == 1;
        }
    }

    public static void recordLogout(String username, String inTime, String outTime) throws SQLException {
        String sql = "INSERT INTO userlogs (username,in_time,out_time) VALUES (?,?,?)";
        try (Connection con = DBConnect.dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, inTime);
            pst.setString(3, outTime);
            pst.executeUpdate();
        }
    }
}
