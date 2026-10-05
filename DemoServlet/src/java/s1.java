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
//import jakarta.servlet.RequestDispatcher;
//
///**
// *
// * @author Shardul
// */
//@WebServlet(urlPatterns = {"/s1"})
//public class s1 extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//            String str =  request.getParameter("txtname");
//            request.setAttribute("a",str);
//            RequestDispatcher rd = request.getRequestDispatcher("s2");
//            rd.forward(request, response);
//        
//    }
//}









import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

@WebServlet(urlPatterns = {"/s1"})
public class s1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("txtid");

        try {

            Connection con = DBConnect.dbCon();

            String sql = "SELECT * FROM student WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String name = rs.getString("name");
                String contact = rs.getString("contact");
                String division = rs.getString("division");
                String address = rs.getString("address");

                request.setAttribute("name", name);
                request.setAttribute("id", id);
                request.setAttribute("contact", contact);
                request.setAttribute("division", division);
                request.setAttribute("address", address);

                RequestDispatcher rd = request.getRequestDispatcher("s2");

                rd.forward(request, response);

            } else {

                response.getWriter().print("Student Not Found");

            }

        } catch (Exception e) {

            response.getWriter().println("Error: " + e);

        }
    }
}