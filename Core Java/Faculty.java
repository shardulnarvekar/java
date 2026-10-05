import com.shardul.faculty.app.*;
import com.shardul.faculty.db.*;


//   * means everything from that folder

class Faculty{
    public static void main(String args[]){
        // C obj = new C();
        A obj = new A();

        System.out.printl(obj.num);
    }
}

// Same package (same folder/package):
// Classes in the same package can access each other's default (package-private) variables and methods.
// No access modifier is needed.
// Different package:
// A class in another package cannot access default members.
// To access them, the member must be declared public (or protected in inheritance cases).

//Even inheritance and object calling wont allow you to use it.
// So for access specifier we can use protected It means that it will allow to access that method or that variable in the same package or by subclasses but not by a different package


// We can make a class final or we can make a method final method final stops overriding and making a class final stops the inheritance