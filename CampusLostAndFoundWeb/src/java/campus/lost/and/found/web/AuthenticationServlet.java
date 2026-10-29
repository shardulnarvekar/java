package campus.lost.and.found.web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebSupport.validForm(request, response)) return;
        try {
            String username = WebSupport.required(request, "username", "student ID / username", 20);
            String password = WebSupport.password(request, "password");
            String role = WebSupport.text(request, "role");
            if (!"USER".equals(role) && !"ADMIN".equals(role)) throw new IllegalArgumentException("Select a role.");
            UserOperations user = UserOperations.login(username, password, role);
            if (user == null) throw new IllegalArgumentException("The username, password or role is incorrect.");
            request.getSession().invalidate();
            HttpSession session = request.getSession(true);
            user.password = "";
            session.setAttribute("user", user);
            session.setAttribute("inTime", new java.sql.Timestamp(System.currentTimeMillis()).toString());
            WebSupport.message(request, "Welcome, " + user.name + ".");
            response.sendRedirect(request.getContextPath() + "/SearchServlet");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (SQLException ex) {
            log("Login database error", ex);
            request.setAttribute("error", "Could not connect to the web project's database. Check MySQL and DBConnect.java; see README.md for setup.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

}
