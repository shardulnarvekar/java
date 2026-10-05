// class Mouse extends Keyboard{
//     public void click(){
//         System.out.println("Mouse Pressed");
//     }
// }

// class Keyboard{
//     int keys=100;
//     String color="White"; //S should be capital in string

//     public void pressed(){
//         System.out.println("Signal Sent"+ " "+color);
//     }

//     public void throwIt(){
//         System.out.println("got hit");
//     }
// }

// class Computer{
//     public static void main(String args[]){
//         int num;
//         num=8;

//         Mouse obj;
//         obj = new Mouse(); //this is called a constructor, this is where the object is made

//         obj.pressed();
//         obj.throwIt();
//         obj.click();

//         System.out.println(obj.keys);

//     }
// }