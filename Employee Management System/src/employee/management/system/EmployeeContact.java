/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employee.management.system;

/**
 *
 * @author Shardul
 */
public class EmployeeContact extends EmployeeDetails {
    String contact;
    String email;
    String address;
    
    public EmployeeContact(int id, String name, String department,
                           String designation, double salary,
                           String contact, String email, String address) {

        super(id, name, department, designation, salary);

        this.contact = contact;
        this.email = email;
        this.address = address;
    }
}
