package com.campus.lostandfound.DAO;

import com.campus.lostandfound.DTO.UserDTO;
import com.campus.lostandfound.Database.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Locale;
import java.util.Vector;

public class UserDAO {
    private Connection conn = null;
    private PreparedStatement prepStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public UserDAO() {
        try {
            conn = new ConnectionFactory().getConn();
            statement = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean registerUser(UserDTO userDTO) {
        if (usernameExists(userDTO.getUsername())) {
            JOptionPane.showMessageDialog(null, "This student ID or username is already registered.");
            return false;
        }
        if (nameExists(userDTO.getFullName())) {
            JOptionPane.showMessageDialog(null, "This name is already registered. Please check your account.");
            return false;
        }
        try {
            String query = "INSERT INTO users (name,location,phone,username,password,usertype) VALUES(?,?,?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userDTO.getFullName());
            prepStatement.setString(2, userDTO.getLocation());
            prepStatement.setString(3, userDTO.getPhone());
            prepStatement.setString(4, userDTO.getUsername());
            prepStatement.setString(5, userDTO.getPassword());
            prepStatement.setString(6, "USER");
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registration successful. Please log in with your new account.");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Registration failed. Please check your information.");
            return false;
        }
    }

    public boolean usernameExists(String username) {
        try {
            String query = "SELECT id FROM users WHERE username=? LIMIT 1";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);
            resultSet = prepStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return true;
        }
    }

    public boolean nameExists(String fullName) {
        try {
            String query = "SELECT id FROM users WHERE name=? LIMIT 1";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, fullName);
            resultSet = prepStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return true;
        }
    }

    public UserDTO getUserProfile(String username) {
        UserDTO dto = new UserDTO();
        try {
            String query = "SELECT id,name,location,phone,username,password,usertype FROM users WHERE username=? LIMIT 1";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) {
                dto.setId(resultSet.getInt("id"));
                dto.setFullName(resultSet.getString("name"));
                dto.setLocation(resultSet.getString("location"));
                dto.setPhone(resultSet.getString("phone"));
                dto.setUsername(resultSet.getString("username"));
                dto.setPassword(resultSet.getString("password"));
                dto.setUserType(resultSet.getString("usertype"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dto;
    }

    public String findUsernameByUsername(String username) {
        try {
            String query = "SELECT username FROM users WHERE username=? LIMIT 1";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) return resultSet.getString("username");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean changePass(String username, String oldPassword, String newPassword) {
        try {
            String query = "UPDATE users SET password=? WHERE username=? AND password=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, newPassword);
            prepStatement.setString(2, username);
            prepStatement.setString(3, oldPassword);
            boolean changed = prepStatement.executeUpdate() > 0;
            JOptionPane.showMessageDialog(null, changed ? "Password changed successfully." : "The current password is incorrect. Your password was not changed.");
            return changed;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not change the password.");
            return false;
        }
    }

    public void addUserDAO(UserDTO userDTO, String userType) {
        registerUser(userDTO);
    }

    public void addFunction(UserDTO userDTO, String userType) {
        registerUser(userDTO);
    }

    public void editUserDAO(UserDTO userDTO) {
        try {
            String query = "UPDATE users SET name=?,location=?,phone=?,usertype=? WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userDTO.getFullName());
            prepStatement.setString(2, userDTO.getLocation());
            prepStatement.setString(3, userDTO.getPhone());
            prepStatement.setString(4, userDTO.getUserType());
            prepStatement.setString(5, userDTO.getUsername());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User information updated.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteUserDAO(String username) {
        try {
            String query = "DELETE FROM users WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User deleted.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet getQueryResult() {
        try {
            resultSet = statement.executeQuery("SELECT * FROM users");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getUserDAO(String username) {
        try {
            String query = "SELECT * FROM users WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);
            resultSet = prepStatement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultSet;
    }

    public void getFullName(UserDTO userDTO, String username) {
        UserDTO profile = getUserProfile(username);
        userDTO.setFullName(profile.getFullName() == null ? username : profile.getFullName());
    }

    public ResultSet getPassDAO(String username, String password) {
        try {
            String query = "SELECT password FROM users WHERE username=? AND password=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);
            prepStatement.setString(2, password);
            resultSet = prepStatement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultSet;
    }

    public void changePass(String username, String password) {
        changePass(username, getUserProfile(username).getPassword(), password);
    }

    public void addUserLogin(UserDTO userDTO) {
        try {
            String query = "INSERT INTO userlogs (username,in_time,out_time) VALUES(?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userDTO.getUsername());
            prepStatement.setString(2, userDTO.getInTime());
            prepStatement.setString(3, userDTO.getOutTime());
            prepStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("User login log skipped: " + ex.getMessage());
        }
    }

    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int colCount = metaData.getColumnCount();

        for (int col = 1; col <= colCount; col++) {
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<>();
            for (int col = 1; col <= colCount; col++) {
                vector.add(resultSet.getObject(col));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
}
