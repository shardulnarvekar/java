/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javasem5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Shardul
 */
public class exceptionassignment {
    public static void main(String args[]){
        int a;
        int b;
        int r;
        int j;
        Scanner sc = new Scanner(System.in);
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String str;
        
        try{
            str=br.readLine();
            a=sc.nextInt();
            b=sc.nextInt();
            j=Integer.parseInt(str);
            r=a/b;
            System.out.println(r);
        }catch(NumberFormatException e){
            System.out.println("Number Format Exception");
        }catch(InputMismatchException e){
            System.out.println("Input Mismatch Exception");
        }catch(ArithmeticException e){
            System.out.println("Dividing By Zero");
        }catch(Exception e){
            System.out.println("Error");
        }
    }
}
