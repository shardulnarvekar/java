package campus.lost.and.found.web;

import java.sql.*;

/** JDBC connection, in the same style as the classroom DBConnect class. */
public class DBConnect {
    public static Connection dbCon() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/campuslostfoundwebdb";
        String user = "root";
        String password = "Nigga@114";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("MySQL JDBC driver is missing from WEB-INF/lib.", ex);
        }
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
