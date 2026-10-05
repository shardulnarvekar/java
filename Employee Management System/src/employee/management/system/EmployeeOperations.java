/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employee.management.system;

/**
 *
 * @author Shardul
 */
import java.sql.*;

public class EmployeeOperations extends EmployeeContact {
    
    public EmployeeOperations(int id, String name, String department,
                              String designation, double salary,
                              String contact, String email, String address) {

        super(id, name, department, designation, salary,
              contact, email, address);
    }
    
    public void addEmployee() {

    try {

        Connection con = DBConnect.dbCon();

        String sql = "INSERT INTO employee "
                   + "(id, name, department, designation, salary, contact, email, address) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);
        pst.setString(2, name);
        pst.setString(3, department);
        pst.setString(4, designation);
        pst.setDouble(5, salary);
        pst.setString(6, contact);
        pst.setString(7, email);
        pst.setString(8, address);

        pst.executeUpdate();

        System.out.println("Employee Added Successfully");

    } catch (Exception e) {

        System.out.println(e);

        }
    }
//    public static void main(String args[]) {
//
//    EmployeeOperations emp = new EmployeeOperations(
//        101,
//        "Shardul",
//        "IT",
//        "Developer",
//        80000,
//        "9876543210",
//        "shardul@gmail.com",
//        "Mumbai"
//    );
//
////    emp.addEmployee();
////    emp.searchEmployee(101);
////    emp.updateEmployee();
//    emp.deleteEmployee(101);
//}
    
    
    public static EmployeeOperations searchEmployee(int id) {

    try {

        Connection con = DBConnect.dbCon();

        String sql = "SELECT * FROM employee WHERE id = ?";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {

            return new EmployeeOperations(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("department"),
                rs.getString("designation"),
                rs.getDouble("salary"),
                rs.getString("contact"),
                rs.getString("email"),
                rs.getString("address")
            );
        }

    } catch (Exception e) {

        System.out.println(e);

    }

    return null;
}
    public void updateEmployee() {

    try {

        Connection con = DBConnect.dbCon();

        String sql = "UPDATE employee SET name = ?, department = ?, "
                   + "designation = ?, salary = ?, contact = ?, "
                   + "email = ?, address = ? WHERE id = ?";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, name);
        pst.setString(2, department);
        pst.setString(3, designation);
        pst.setDouble(4, salary);
        pst.setString(5, contact);
        pst.setString(6, email);
        pst.setString(7, address);
        pst.setInt(8, id);

        pst.executeUpdate();

        System.out.println("Employee Updated Successfully");

    } catch (Exception e) {

        System.out.println(e);

    }
}
    public static void deleteEmployee(int id) {

    try {

        Connection con = DBConnect.dbCon();

        String sql = "DELETE FROM employee WHERE id = ?";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        pst.executeUpdate();

        System.out.println("Employee Deleted Successfully");

    } catch (Exception e) {

        System.out.println(e);

    }
}
}
