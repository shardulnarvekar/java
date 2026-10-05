/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student.management.system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

/**
 *
 * @author Shardul
 */
public class Demo {
    public static void main(String args[]){
    String sql = "select Sname from payment where Sr_No=4";
    //its 3306 for mysql and changes for other databases
    String url = "jdbc:mysql://localhost:3306/bus_logistics";
    String user = "root";
    String password = "Nigga@114";
    //note this is very important
    //Connection itself is an interface, so we cant directly create an object
    //Thus, we use driver Manager, its basically a class
    //Using that we use method, getConnection() n
    try{
    Connection con = DriverManager.getConnection(url,user,password);
    Statement st=con.createStatement();
//  st.executeQuery(sql);//this fires the select query, print the table
    //if we want to fire, insert upadate, we use executeupdate
    //remember the above query does not return a string, you may think that way
    //it returns something called as result set
    ResultSet rs = st.executeQuery(sql);
    //here we are talking about pointers, bydefault the pointer points to the
    //first location, you have to iterate, thus we write rs.next
    rs.next();
    String name = rs.getString(1);//1 is basically the column number
        System.out.println(name);
    con.close();
    }
    catch(Exception e){
    System.out.println(e);
    } 
    
}
}
