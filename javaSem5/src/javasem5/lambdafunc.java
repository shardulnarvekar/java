/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javasem5;

/**
 *
 * @author Shardul
 */


interface Welcome1{
    void disp();
}

interface Welcome2{
    void disp(int a, int b);
}
public class lambdafunc {
    public static void main(String args[]){
        Welcome1 x =()->{
            System.out.println("Hello");
        };
        x.disp();
        
        Welcome2 x1=(a,b)->{
            System.out.println("Sum="+(a+b));
        };
        x1.disp(10,20);
    }
}
