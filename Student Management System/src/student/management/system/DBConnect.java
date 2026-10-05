/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student.management.system;

/**
 *
 * @author Shardul
 */

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnect {
    public static Connection dbCon() {

        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/studentdb";
        String user = "root";
        String password = "Nigga@114";

        try {
            con = DriverManager.getConnection(url,user,password);

            System.out.println("Database Connected");

        } catch (Exception e) {
            System.out.println(e);
        }

        return con;
    }
    public static void main(String args[]){
        dbCon();
    }
}