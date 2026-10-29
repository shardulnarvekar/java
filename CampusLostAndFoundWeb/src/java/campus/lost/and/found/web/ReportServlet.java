package campus.lost.and.found.web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = WebSupport.user(request, response);
        if (user == null) return;
        try {
            if (!WebSupport.text(request, "id").isEmpty()) {
                LostItemOperations item = LostItemOperations.searchItem(WebSupport.id(request));
                if (item == null) { response.sendError(404); return; }
                if (!item.canEdit(user)) { response.sendError(403); return; }
                request.setAttribute("item", item);
            }
            request.getRequestDispatcher(WebSupport.text(request, "action").equals("delete")
                    ? "/WEB-INF/views/delete.jsp" : "/WEB-INF/views/report-form.jsp").forward(request, response);
        } catch (IllegalArgumentException ex) { response.sendError(400); }
        catch (SQLException ex) { log("Report form failed", ex); response.sendError(503); }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = WebSupport.user(request, response);
        if (user == null || !WebSupport.validForm(request, response)) return;
        String action = WebSupport.text(request, "action");
        LostItemOperations existing = null;
        try {
            int id = 0;
            if (!action.equals("add")) {
                id = WebSupport.id(request);
                existing = LostItemOperations.searchItem(id);
                if (existing == null) { response.sendError(404); return; }
                if (!existing.canEdit(user)) { response.sendError(403); return; }
            }
            if (action.equals("delete")) {
                if (!LostItemOperations.deleteItem(id, user.username)) throw new IllegalArgumentException("This report changed. Reload before deleting.");
                WebSupport.message(request, "Report deleted.");
                response.sendRedirect(request.getContextPath() + "/SearchServlet?view=mine");
                return;
            }
            if (!action.equals("add") && !action.equals("edit")) { response.sendError(400); return; }
            String name = WebSupport.required(request, "itemName", "item name", 100);
            String category = WebSupport.required(request, "category", "category", 50);
            if (!java.util.Arrays.asList(LostItemOperations.CATEGORIES).contains(category)) throw new IllegalArgumentException("Select a listed category.");
            String location = WebSupport.required(request, "pickupLocation", "found location", 100);
            String date = WebSupport.required(request, "pickupTime", "date and time found", 16);
            java.sql.Timestamp pickupTime;
            try {
                java.time.LocalDateTime parsed = java.time.LocalDateTime.parse(date);
                pickupTime = java.sql.Timestamp.valueOf(parsed);
            } catch (java.time.DateTimeException ex) { throw new IllegalArgumentException("Enter a valid date and time found."); }
            String person = WebSupport.required(request, "pickupPerson", "finder's name", 50);
            String contact = WebSupport.required(request, "contact", "contact number", 50);
            String description = WebSupport.text(request, "description");
            if (description.length() > 2000) throw new IllegalArgumentException("Keep the description within 2,000 characters.");
            LostItemOperations item = new LostItemOperations(id, name, category, location, pickupTime, description, person, contact, user.username);
            if (action.equals("add")) item.addItem();
            else if (!item.updateItem(user.username)) throw new IllegalArgumentException("This report changed. Reload before editing.");
            WebSupport.message(request, action.equals("add") ? "Report added successfully." : "Report updated successfully.");
            response.sendRedirect(request.getContextPath() + "/ItemServlet?id=" + item.id);
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("item", existing);
            request.getRequestDispatcher(action.equals("delete") ? "/WEB-INF/views/delete.jsp" : "/WEB-INF/views/report-form.jsp").forward(request, response);
        } catch (SQLException ex) {
            log("Report save failed", ex);
            request.setAttribute("error", "Could not save the report. Check the database and try again.");
            request.setAttribute("item", existing);
            request.getRequestDispatcher(action.equals("delete") ? "/WEB-INF/views/delete.jsp" : "/WEB-INF/views/report-form.jsp").forward(request, response);
        }
    }

}
