/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author Shardul
 */
@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                PrintWriter out = response.getWriter();

                String username = request.getParameter("username");
                String password = request.getParameter("password");

                try {

                    Connection con = DBConnection.dbCon();

                    String sql =
                            "SELECT * FROM students WHERE username=? AND password=?";

                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, username);
                    ps.setString(2, password);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {

                        // Get student name from database
                        String studentName = rs.getString("sname");

                        // Create session
                        HttpSession session = request.getSession();

                        // Store values in session
                        session.setAttribute("name", studentName);
                        session.setAttribute("username", username);

                        // Redirect to second servlet
                        response.sendRedirect("Welcome");

                    } else {

                        out.println("Invalid Username or Password");
                        out.println("Try Again");

                    }

                    con.close();

                } catch (Exception e) {

                    e.printStackTrace();

                    out.println("Database Error");

                }
            }
        }
