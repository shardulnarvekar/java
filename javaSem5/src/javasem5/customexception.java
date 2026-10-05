/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javasem5;

import java.util.Scanner;

class AEx extends Exception{
    AEx(String str){
        System.out.println(str);
    }
}

/**
 *
 * @author Shardul
 */
public class customexception {
    public static void main(String args[]) throws AEx{
        int a;
        System.out.println("Enter Student Percentage");
        Scanner sc = new Scanner(System.in);
        a=sc.nextInt();
        if(a<0 || a>100)    
            throw new AEx("Invalid Percentage");
    }
    
//the above code is not handling the exception, we are raising the exception
    
    
    
    
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
//    {
//        int a;
//        System.out.println("Enter Student Percentage");
//        Scanner sc = new Scanner(System.in);
//        try{
//            a=sc.nextInt();
//            if(a<0 || a>100){
//                throw new ArithmeticException();
//            }
//        }
//        catch(ArithmeticException e){
//            System.out.println("Number should be between 0 and 100");
//        }
//    }
    
    
    
    
}
