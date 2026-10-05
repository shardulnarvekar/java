package javasem5;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

    Connection x = null;
    
    public Connection dbCon() {

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String password = "Nigga@114";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            x = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println(e);
            }

        return x;
    }
    
    
}