/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Shardul
 */


import java.sql.*;


public class DBConnection {

    public static Connection dbCon() {

        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/studentdb";
        String user = "root";
        String password = "Nigga@114";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            
            e.printStackTrace();
        }

        return con;
    }
}
