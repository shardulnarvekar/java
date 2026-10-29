package campus.lost.and.found.web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ClaimServlet")
public class ClaimServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = WebSupport.user(request, response);
        if (user == null || !WebSupport.validForm(request, response)) return;
        LostItemOperations item = null;
        try {
            int id = WebSupport.id(request);
            item = LostItemOperations.searchItem(id);
            if (item == null) { response.sendError(404); return; }
            boolean changed;
            if (WebSupport.text(request, "action").equals("handover")) {
                if (!"ADMIN".equals(user.userType)) { response.sendError(403); return; }
                String recipient = WebSupport.required(request, "recipient", "recipient student ID / username", 50);
                changed = LostItemOperations.handoverItem(id, user.username, recipient);
            } else if (WebSupport.text(request, "action").equals("claim")) {
                String name = WebSupport.required(request, "claimant", "claimant name", 50);
                String contact = WebSupport.required(request, "claimContact", "claimant contact", 50);
                changed = LostItemOperations.claimItem(id, user.username, name, contact);
            } else { response.sendError(400); return; }
            if (!changed) throw new IllegalArgumentException("This item is already claimed or handed over. Reload to see its current status.");
            WebSupport.message(request, "Item status updated successfully.");
            response.sendRedirect(request.getContextPath() + "/ItemServlet?id=" + id);
        } catch (IllegalArgumentException ex) {
            request.setAttribute("item", item);
            request.setAttribute("error", ex.getMessage());
            if (item == null) { response.sendError(400); return; }
            request.getRequestDispatcher("/WEB-INF/views/details.jsp").forward(request, response);
        } catch (SQLException ex) { log("Claim failed", ex); response.sendError(503); }
    }

}
