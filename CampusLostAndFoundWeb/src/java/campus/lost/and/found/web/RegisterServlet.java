package campus.lost.and.found.web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebSupport.validForm(request, response)) return;
        try {
            String name = WebSupport.required(request, "name", "full name", 45);
            String location = WebSupport.required(request, "location", "location", 45);
            String phone = WebSupport.required(request, "phone", "phone number", 20);
            String username = WebSupport.required(request, "username", "student ID / username", 20);
            if (!username.matches("[A-Za-z0-9_.-]{1,20}")) throw new IllegalArgumentException("Use letters, numbers, dots, hyphens or underscores for your username.");
            String password = WebSupport.password(request, "password");
            if (!password.equals(request.getParameter("confirmPassword"))) throw new IllegalArgumentException("Passwords do not match.");
            new UserOperations(name, location, phone, username, password, "USER").registerUser();
            WebSupport.message(request, "Account created. Sign in with the Student / User role.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } catch (SQLException ex) {
            log("Registration database error", ex);
            request.setAttribute("error", ex.getMessage().startsWith("This name") || "23000".equals(ex.getSQLState())
                    ? "This name or username is already registered." : "Unable to register. Check the database setup and try again.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

}
