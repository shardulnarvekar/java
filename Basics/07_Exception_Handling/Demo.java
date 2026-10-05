//Types of Errors

//Compile time errors- Erros shown by the compiler

//Logical Error- errors when the program runs successfully, but the output is not what we wanted (bugs)

//Run time error- Code is working, but due to some reason it stopped working, during run time, eg. file is missing, divide 2 numbers and the denominator is zero

//We have to handle the critical statements

class Demo{
    public static void main(String args[]){
        int i=4;
        int j=5;

        int result = 0; //note you initialized the result as zero, got an error otherwise
        int arr[]={2,3,4};
        
        try{

            result=i/j;
            if(result==0)
                throw new ArithmeticException();
            
            System.out.println(arr[4]);
            
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Array index out of bound");
        }
        catch(ArithmeticException e){
            System.out.println("Dividing with 0 nigga");
        }
        catch(Exception e){
            System.out.println("Problem");
        }
        finally{
            System.out.println("Byeeee");
        }
        

        System.out.println(result);

        
    }
}

//when we want to throw the exception, we basically create the object of the exception
//basically throwing the exception of a particular case, without the problem every happening


//there are certain exception, where the compiler will force you to use the try catch block. And handle that particular critical statement- called checked exception

//io- arise when we do some input output with the file 


//if we throw exception, from function to function, we use the word "throws"

//finally block, always executes, even if we got an exception, or didnt get an exception