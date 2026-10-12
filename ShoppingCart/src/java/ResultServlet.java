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

/**
 *
 * @author Shardul
 */
@WebServlet(urlPatterns = {"/ResultServlet"})
public class ResultServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        out.println("<html><body>");
        out.println("<h2>Exam Result</h2>");

        if (session != null &&
                Boolean.TRUE.equals(session.getAttribute("attempted"))) {

            out.println("<h3>Your Score: "
                    + session.getAttribute("score") + "/3</h3>");

        } else {
            out.println("<h3>Please submit the exam first.</h3>");
        }

        out.println("<br><a href='exam.html'>Back to Exam</a>");
        out.println("</body></html>");
    }
}
