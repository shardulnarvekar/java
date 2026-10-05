//collection is a concept

//Collection is an interface

//Note the upper and lowercase c

//Collection API provides, ArrayList
//Use you ArrayList to make a dynamic array- but this belong to a package. or just List

//ArrayList implements List, and List extends collection
//Hashset also extends collection

//Now even when we use array strings, they all belong to the package java.lang, but by default every java file has a java.lang package involved

import java.util.ArrayList;
import java.util.HashSet;

class Demo{
    public static void main(String[] args) {
        // ArrayList<Integer> nums = new ArrayList<>();
        // nums.add(4);
        // nums.add(5);
        // nums.add(9);
        // nums.add(1);

        // // nums.add("Shardul");

        // for (int n : nums){
        //     System.out.println(n);
        // }

        // System.out.println(nums);

        // System.out.println(nums.get(2));






        HashSet<Integer> nums = new HashSet<>(); //we can only have unique values
        





        for (int n : nums){
            System.out.println(n);
        }
    }



}

//This array is a class, so you can store any type of element in it. 

//using generics, we can specify the type