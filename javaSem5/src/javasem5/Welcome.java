/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javasem5;

/**
 *
 * @author Shardul
 */

interface Welcome{
    void disp();
}


class Sample implements Welcome{
    public void disp(){
        System.out.println("yo");
        show();
    }
    void show(){
        System.out.println("Hi");
        demo();
    }
    
    public static void main(String args[]){
        Sample s = new Sample();
        s.disp();//cannot call disp directly without the object
        demo();
//        show();
//        show(); you cant directly call show, its not possible
    }
    public static void demo(){
        System.out.println("demo..");
    }
}
//we do not need object to call a static method
//cannot call non static fucntion, from static function

//can call non static function from non static function


//can call static function from static function

//from main we can directly call demo

//we can call static function from non static function 