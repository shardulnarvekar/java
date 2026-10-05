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
@WebServlet(urlPatterns = {"/Welcome"})
public class Welcome extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

        PrintWriter out = response.getWriter();

        // Get existing session
        HttpSession session = request.getSession(false);

        if (session != null) {

            String studentName =
                    (String) session.getAttribute("name");

         

                    out.println("<html>");
                    out.println("<body>");

                    out.println("<h2>Welcome " + studentName + "</h2>");

                    out.println("<p>Login successful.</p>");

                    out.println(
                        "<form action='LogoutServlet' method='post'>" +
                        "<input type='submit' value='Logout'>" +
                        "</form>"
                    );

                    out.println("</body>");
                    out.println("</html>");

            

        } else {

            response.sendRedirect("login.html");

        }
    }
}
