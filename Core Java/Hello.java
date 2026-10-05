//Jshell - tool in java that lets you write and run java code, one line at a time, without creating a full java program

//To compile a code, we have to open terminal, and type "javac (filename.java)" in the terminal to compile it.


//JVM (Java Virtual Machine executes the java code we write)
//It wont execute the code directly
//Java is platform independent, application runs on any machine, irrespective of your Hardware and Operating System. (That machine should have JVM)eg. we cannot run JVM on iOS

//JVM will run your code, we have to give it, not a java code, but a byte c ode
//Thus we use JAVAC (Java Compiler) it does the conversion
//We can pass multiple files to JVM, but it only needs the one file from which the execution starts (We specify this file). It should have a main method.
// This is -> public static void main -> We have to pass certain parameters ->(String a[])


// class Hello 
// {  
//     public static void main(String a[])
//     {
//         System.out.print("Hello World!");
//     }
// }

//The Hello.class which got created, it is called the byte code its extension is .class
//That runs in the jvm



//There is something called as JRE level, inside that is the JVM
//JRE- Java Runtime Environment

//jvm with all the libraries, is a part of jre


//Layers are JDK(Java Developemnet Kit)-> JRE -> JVM

//to run my application, only jre inside which jvm is required. jdk is not required, to run my application on any machine
//Java- WORA (Write once and run anywhere)


//JDK is what developers need, with that, we get the jre and jvm


// class Hello 
// {  
//     public static void main(String a[])
//     {
//         System.out.println(3+5);
//         System.out.println(7+5);
//     }
// }

//println, means print new line


// class Hello 
// {  
//     public static void main(String a[])
//     {
//         int num1 = 3;
//         int num2 = 4;
//         System.out.println(num1+num2);
//     }
// }

//biggest confusion, in jdk 22 and higher, even if you forgot to encapsulate the code in a class, it does it automatically.
//The byte code, it generates (.class file), it takes the name from the source file (.java)


//if we encapsulate everything in a class, which is not Hello, eg. class apple, it will make apple.class file. 
//if we have multiple classes with multiple different names, it will create a multiple class files
//if we define a class a public, then the public class name and .java file name should match, it will throw and error otherwise



//practice
// class Hello
// {
//     public static void main(String a[])
//     {
//     System.out.println(8);
//     }
// }

//these are types of int
//int 4 byte (-2^31)to(2^31-1)
//long 8 bytes (-2^63)to(2^63-1) note: also needs l with the number while saving
//short 2bytes (-2^15)to(2^15-1)
//byte (-2^7)to(2^7-1) =>-128 to 127

//above you can calculate the rest



//float has float and double and by defualt it uses double and float
//double num=5.6;
//float num=5.6f;      <---- the f we added, specifing its a float


class Hello{
    public static void main(String a[]){
        int num  = 9;
        byte by =129;

        long l = 5544l;
        float f =6.5f;

        System.out.println(result);
    }
}

//basic concept of typecasting, implicite and explicite conversion

//b=(byte)a; converting a into a byte and then assigning the value of b. note: both a and b are two different data types, b is byte, a is int. Going bigger to small 