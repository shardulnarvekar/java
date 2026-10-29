package campus.lost.and.found.system;

import java.sql.*;

/** JDBC connection, in the same style as the classroom DBConnect class. */
public class DBConnect {
    public static Connection dbCon() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/lostfounddb";
        String user = "root";
        String password = "Nigga@114";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        try (Connection con = dbCon()) {
            System.out.println("Database Connected: " + con.getCatalog());
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
}
