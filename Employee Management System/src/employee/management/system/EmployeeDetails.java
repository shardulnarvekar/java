/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employee.management.system;

/**
 *
 * @author Shardul
 */
public class EmployeeDetails extends Employee{
    
    String designation;
    double salary;
    
    public EmployeeDetails(int id, String name, String department,
                           String designation, double salary) {

        super(id, name, department);

        this.designation = designation;
        this.salary = salary;
    }
}
