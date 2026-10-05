public class ablayer {
    public static void main(String args[]){
    

        // Laptop l = new Laptop();
        // Desktop d = new Desktop();

        Computer l = new Laptop();
        Computer d = new Desktop();
        Dev d1= new Dev();

        d1.code(d);
    
}


}
class Dev{
    public void code(Computer d){
        System.out.println("Coding");
        d.compile();
    }

    
}


class Laptop implements Computer, Portable{
    public void compile(){
        System.out.println("Compiling");
    }
}

class Desktop implements Computer{ //not extends as we used interface
    public void compile(){
        System.out.println("Compiling fasttt");
    }
     
    public void move(){
        System.out.println("Moving");
    }

}

// abstract class Computer{
//     public abstract void compile();{

//     }
// }

interface Computer{
    int price=2000  ;
    void compile();
}

interface Portable{
    void move();
}

//Very very vere important code, understand, shit is complicated 
// The Computer class is a concept and the compile function inside Computer is also a concept so it is not doing anything so we can declare it as abstract if a method inside a class is abstract the class should be abstract but if a class is abstract it's not it's not compulsory that all methods in it need to be abstract


// Every class which is extending the abstract class must implement the abstract method in it

// If we have an abstract class with all abstract methods then we can do is we can replace it with interface class
//Everything in interface will be by default, public and abstract, just start and write void compile() .... etc

// These abstract classes are basically used as reference and we make objects which represent other classes anyways so **** them
