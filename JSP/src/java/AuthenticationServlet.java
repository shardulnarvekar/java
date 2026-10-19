/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Shardul
 */
public class AuthenticationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String a = request.getParameter("t1");
            String b = request.getParameter("t2");
            HttpSession session = request.getSession();
            if(a.equals("abc") && b.equals("1234")){
                String x = "PQR";
                session.setAttribute("user",x);
                response.sendRedirect("home.jsp");
            }
            else{
                String str = "Invalid Username or Password";
                session.setAttribute("message",str);
                response.sendRedirect("login.jsp");
            }
    }
}
