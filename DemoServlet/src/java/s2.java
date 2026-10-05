///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
///**
// *
// * @author Shardul
// */
//@WebServlet(urlPatterns = {"/s2"})
//public class s2 extends HttpServlet {
//
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//            String p = (String)request.getAttribute("a");
//            
//            PrintWriter pw = response.getWriter();
//            pw.print("Hello "+p);
//        
//    }
//
//}
//
//
//
//








import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/s2"})
public class s2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = (String) request.getAttribute("name");
        String id = (String) request.getAttribute("id");
        String contact = (String) request.getAttribute("contact");
        String division = (String) request.getAttribute("division");
        String address = (String) request.getAttribute("address");

        PrintWriter pw = response.getWriter();

        pw.println("Student Details");
        

        pw.println("Name: " + name);
        

        pw.println("ID: " + id);
        

        pw.println("Contact: " + contact);
        

        pw.println("Division: " + division);
        

        pw.println("Address: " + address);
    }
}