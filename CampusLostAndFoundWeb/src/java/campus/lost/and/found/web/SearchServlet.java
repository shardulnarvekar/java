package campus.lost.and.found.web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = WebSupport.user(request, response);
        if (user == null) return;
        String view = WebSupport.text(request, "view");
        if (!view.equals("mine") && !view.equals("history")) view = "all";
        request.setAttribute("view", view);
        String submitter = view.equals("mine") ? user.username : "";
        String claimant = view.equals("history") && !user.userType.equals("ADMIN") ? user.username : "";
        String category = WebSupport.text(request, "category");
        if (category.isEmpty()) category = "All";
        String status = WebSupport.text(request, "status");
        if (status.isEmpty()) status = "All";
        String name = WebSupport.text(request, "name");
        String location = WebSupport.text(request, "location");
        String keyword = WebSupport.text(request, "keyword");
        boolean history = view.equals("history");
        boolean oldest = WebSupport.text(request, "sort").equals("oldest");
        int page = 1;
        try { page = Math.max(1, Integer.parseInt(WebSupport.text(request, "page"))); }
        catch (NumberFormatException ex) { }
        try {
            int total = LostItemOperations.countItems(name, category, location, status, submitter, claimant, history, keyword);
            int pages = Math.max(1, (total + 17) / 18);
            page = Math.min(page, pages);
            request.setAttribute("items", LostItemOperations.searchItems(name, category, location, status, submitter, claimant, history, keyword, page, 18, oldest));
            request.setAttribute("total", total);
            request.setAttribute("pageNumber", page);
            request.setAttribute("pages", pages);
        } catch (SQLException ex) {
            log("Report search failed", ex);
            request.setAttribute("error", "Could not load reports. Check MySQL and the web database setup.");
            request.setAttribute("items", new java.util.ArrayList<LostItemOperations>());
            request.setAttribute("total", 0);
            request.setAttribute("pageNumber", 1);
            request.setAttribute("pages", 1);
        }
        request.getRequestDispatcher("/WEB-INF/views/reports.jsp").forward(request, response);
    }

}
