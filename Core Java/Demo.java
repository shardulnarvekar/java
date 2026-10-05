//Everything in java, should be in a class


//Execution, file name for compiling, class name for running

// class Demo{
//     public static void main(String args[]){
//         int num=5;
//         double d=9.6;
//         float f=8.5f; //more space than double
//         long l=53443;
//         char c='A';
//         System.out.println("Hello World!");
//         System.out.println(num+d);
//         show();
//     } //we are passing an array


//     public static void show(){
//         System.out.println("show");
//     }
// }



class Demo{
    public static void main(String args[]){
        int num1=2;
        int num2=9;

        boolean result = num1 > num2;

        System.out.println(result);

        // if(num1>num2){
        //     System.out.println("yo");
        // }
        // else if(num1<num2){
        //     System.out.println("hi");
        // }
        // else{
        //     System.out.println("go");
        // }

        // switch(num1){
        //     case 1:
        //         System.out.println("One");
        //         break;
        //     case 2:
        //         System.out.println("Two");
        //         break;
        //     default:
        //         System.out.println("Nothing");
        // }

        int j = 0;
        while(j<5){
            System.out.println(j);
            j++;
        }

        for(int i=0; i<5; i++){
            System.out.println(i);
        }
    }
}