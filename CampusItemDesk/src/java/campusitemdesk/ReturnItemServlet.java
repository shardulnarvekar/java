package campusitemdesk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ReturnItemServlet")
public class ReturnItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String recipient = request.getParameter("returnedTo");
        if (recipient == null || recipient.trim().isEmpty() || recipient.length() > 100) {
            session.setAttribute("message", "Enter the recipient's name (up to 100 characters).");
            response.sendRedirect("items.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String sql = "UPDATE items SET status='Returned',returned_to=? WHERE id=? AND status='Available'";
            try (Connection con = DBConnect.dbCon();
                    PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, recipient.trim());
                ps.setInt(2, id);
                int rows = ps.executeUpdate();
                session.setAttribute("message", rows == 1 ? "Item marked as returned." : "Item not found or already returned.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Invalid item number.");
        } catch (SQLException e) {
            log("Could not return item", e);
            session.setAttribute("message", "Could not update. Check MySQL and DBConnect.java.");
        }
        response.sendRedirect("items.jsp");
    }
}
