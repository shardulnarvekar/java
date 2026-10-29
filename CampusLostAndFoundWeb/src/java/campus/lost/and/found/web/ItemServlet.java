package campus.lost.and.found.web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ItemServlet")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (WebSupport.user(request, response) == null) return;
        try {
            LostItemOperations item = LostItemOperations.searchItem(WebSupport.id(request));
            if (item == null) { response.sendError(404); return; }
            request.setAttribute("item", item);
            request.getRequestDispatcher("/WEB-INF/views/details.jsp").forward(request, response);
        } catch (IllegalArgumentException ex) { response.sendError(400); }
        catch (SQLException ex) { log("Report lookup failed", ex); response.sendError(503); }
    }

}
