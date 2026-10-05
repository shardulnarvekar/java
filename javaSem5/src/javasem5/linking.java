/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javasem5;

import java.util.LinkedList;

/**
 *
 * @author Shardul
 */
public class linking {
    public static void main(String args[]){
        LinkedList<String> x = new LinkedList<>();
        x.addFirst("A");
        x.addLast("B");
        System.out.println(x);
        
        x.addFirst("C");
        System.out.println(x);
        x.add(1,"F"); //this is add at index
        System.out.println(x);
        x.add("E"); //this is bydefualt adding the element at the end of the list
        System.out.println(x);
        x.remove(); //this by defualt removes the first element
        System.out.println(x);
        x.remove(1); //removed element from index 1
        System.out.println(x);
        //Similarly there is removefirst and removelast
        
    }
}
