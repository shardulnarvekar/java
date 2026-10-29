package campus.lost.and.found.web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (WebSupport.user(request, response) == null) return;
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = WebSupport.user(request, response);
        if (user == null || !WebSupport.validForm(request, response)) return;
        try {
            String oldPassword = WebSupport.password(request, "oldPassword");
            String newPassword = WebSupport.password(request, "newPassword");
            if (!newPassword.equals(request.getParameter("confirmPassword"))) throw new IllegalArgumentException("New passwords do not match.");
            if (!UserOperations.changePassword(user.username, oldPassword, newPassword)) throw new IllegalArgumentException("Your current password is incorrect.");
            WebSupport.message(request, "Password changed successfully.");
            response.sendRedirect(request.getContextPath() + "/ProfileServlet");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
        } catch (SQLException ex) { log("Password update failed", ex); response.sendError(503); }
    }

}
