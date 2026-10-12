/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Shardul
 */
@WebServlet(urlPatterns = {"/ExamServlet"})
public class ExamServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            int score = 0;

        if ("New Delhi".equals(request.getParameter("q1")))
            score++;

        if ("Mars".equals(request.getParameter("q2")))
            score++;

        if ("366".equals(request.getParameter("q3")))
            score++;

        HttpSession session = request.getSession();

        session.setAttribute("score", score);
        session.setAttribute("attempted", true);

        response.sendRedirect("ResultServlet");
    }
}
