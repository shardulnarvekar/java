package campus.lost.and.found.web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = WebSupport.user(request, response);
        if (user == null || !WebSupport.validForm(request, response)) return;
        HttpSession session = request.getSession(false);
        try {
            UserOperations.recordLogout(user.username, (String) session.getAttribute("inTime"), new java.sql.Timestamp(System.currentTimeMillis()).toString());
        } catch (SQLException ex) { log("Could not save logout log", ex); }
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

}
