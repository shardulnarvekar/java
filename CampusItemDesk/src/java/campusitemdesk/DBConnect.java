package campusitemdesk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    public static Connection dbCon() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/campusitemdeskdb";
        String user = "root";
        String password = "Nigga@114";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL driver was not found.", e);
        }

        return DriverManager.getConnection(url, user, password);
    }
}
