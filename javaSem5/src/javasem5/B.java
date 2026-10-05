/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javasem5;

abstract class A{
    abstract void disp();
}



public class B extends A{
    public void show(){
    System.out.println("Method of B");
    }
    @Override
    public void disp(){
    System.out.println("Method of A");
    }
}


