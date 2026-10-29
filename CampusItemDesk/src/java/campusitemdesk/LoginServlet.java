package campusitemdesk;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        // A fixed classroom login, like the AuthenticationServlet in the JSP project.
        if ("campus".equals(username) && "1234".equals(password)) {
            request.changeSessionId();
            session.setAttribute("user", username);
            response.sendRedirect("items.jsp");
        } else {
            session.setAttribute("message", "Invalid username or password.");
            response.sendRedirect("login.jsp");
        }
    }
}
