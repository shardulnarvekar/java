/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javasem5;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shardul
 */
public class arrays {
    public static void main(String args[]){
        List<String> str = new ArrayList<>();
        str.add("A");
        str.add(1,"B");
        str.add(2,"C");
        System.out.println(str);
        str.set(1,"F");
        System.out.println(str);
        String a;
        String b;
        a=str.get(0);
        b=str.get(1);
        for(int i=0; i<str.size(); i++){
            System.out.println(str.get(i));
        }
    }
}
