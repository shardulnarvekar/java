/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employee.management.system;
import java.sql.*;

/**
 *
 * @author Shardul
 */
public class DBConnect {

    public static Connection dbCon() {

        Connection con = null;

            String url = "jdbc:mysql://localhost:3306/employee_db";
        String user = "root";
        String password = "Nigga@114";

        try {

            con = DriverManager.getConnection(url, user, password);

            System.out.println("Database Connected");

        } catch (Exception e) {

            System.out.println(e);

        }

        return con;
    }

    public static void main(String args[]) {
        dbCon();
    }
}
