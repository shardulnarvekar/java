// String is a class in java, not just a primitive data type,


// class Strings{
//     public static void main(String args[]){
//         // StringBuffer obj = new StringBuffer("Shardul");  
//         // obj.append(" Naverkar");

//         // System.out.println(obj);



//         // String s = "Jeel";
//         // String s1 = "Jeel";



//         String s="Shardul";
//         s.lenght
//     }

    

//     //above both are pointing to the same, string pool, where only 1 Jeel is stored

//     //Strings are immutable, if we want to make it mutuable, we can do that, by using Stringbuffer
// }


// class arrays{
//     public static void main(String args[]){
//         int num[]={8,6,7,4,9};

//         int num1[] = new int[5];

//         int num2[] = {6,8,3,2};

//         // for(int i=0; i<num.length; i++){
//         //     System.out.println(num[i]);
//         // }

//         for(int n: num){
//             System.out.println(n);
//         }

//         //this is enhanced for loop
//     }
// }



class Student{
    int rollno;
    String name;
    public Student(int rollno, String name){
        this.rollno=rollno;
        this.name=name;
    }

    public String toString(){
        return rollno+" "+name;
    }
}

class telusko{
    public static void main(String args[]){
        Student s = new Student(10,"Shardul");
        System.out.println(s);


        //if i want to create multiple objs referrence, not objs, remember the syntax

        Student obj[] = new Student[2];
        obj[0]= new Student(10,"Shardul");
        obj[1]= new Student(11,"Jeel");
        // obj[]= new Student(10,"Tejas");

        for(Student i: obj){
            System.out.println(i);
        }

    }
}


//Every class in Java Extends a object class, object is a special class in java
//The above even without system.out.println it call the toString method, so we override it

// So basically when we compile we get files, class files, each file will have one class, and so all these class files will be stored in an folder, and packaged eg. jar file, WAR file

//every folder is called a package, which will have a group of class associated with something, like database, utiliy etc.

//folder inside folder, package inside package