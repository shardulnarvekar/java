package campusitemdesk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddItemServlet")
public class AddItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String name = request.getParameter("itemName");
        String location = request.getParameter("location");
        String date = request.getParameter("foundDate");
        String contact = request.getParameter("contact");
        String description = request.getParameter("description");

        if (name == null || name.trim().isEmpty() || name.length() > 100
                || location == null || location.trim().isEmpty() || location.length() > 100
                || date == null || date.trim().isEmpty()
                || contact == null || contact.trim().isEmpty() || contact.length() > 50
                || (description != null && description.length() > 500)) {
            session.setAttribute("message", "Fill in all required fields and follow the field limits.");
            response.sendRedirect("add-item.jsp");
            return;
        }

        Date foundDate;
        try {
            foundDate = Date.valueOf(LocalDate.parse(date));
        } catch (DateTimeParseException e) {
            session.setAttribute("message", "Enter a valid date.");
            response.sendRedirect("add-item.jsp");
            return;
        }

        String sql = "INSERT INTO items (item_name,location,found_date,contact,description) VALUES (?,?,?,?,?)";
        try (Connection con = DBConnect.dbCon();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            ps.setString(2, location.trim());
            ps.setDate(3, foundDate);
            ps.setString(4, contact.trim());
            ps.setString(5, description == null ? "" : description.trim());
            ps.executeUpdate();
            session.setAttribute("message", "Item added.");
            response.sendRedirect("items.jsp");
        } catch (SQLException e) {
            log("Could not add item", e);
            session.setAttribute("message", "Could not save. Check MySQL and DBConnect.java.");
            response.sendRedirect("add-item.jsp");
        }
    }
}
