package campus.lost.and.found.web;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

/** Small shared methods for the same request/session approach as the JSP example. */
public class WebSupport {
    public static String text(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        return value == null ? "" : value.trim();
    }

    public static String required(HttpServletRequest request, String key, String label, int max) {
        String value = text(request, key);
        if (value.isEmpty()) throw new IllegalArgumentException("Enter " + label + ".");
        if (value.length() > max) throw new IllegalArgumentException(label + " must be at most " + max + " characters.");
        return value;
    }

    public static String password(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        if (value == null || value.trim().isEmpty() || value.length() > 200)
            throw new IllegalArgumentException("Enter a password of 1 to 200 characters.");
        return value;
    }

    public static int id(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(text(request, "id"));
            if (id > 0) return id;
        } catch (NumberFormatException ex) { }
        throw new IllegalArgumentException("Choose a valid report.");
    }

    public static User user(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store");
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) response.sendRedirect(request.getContextPath() + "/login.jsp");
        return user;
    }

    // A hidden form value stored in the session prevents submissions from another site.
    public static String token(HttpSession session) {
        String token = (String) session.getAttribute("formToken");
        if (token == null) {
            token = UUID.randomUUID().toString();
            session.setAttribute("formToken", token);
        }
        return token;
    }

    public static boolean validForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session != null && text(request, "token").equals(session.getAttribute("formToken"))) return true;
        response.sendError(403, "This form has expired. Reload the page and try again.");
        return false;
    }

    public static void message(HttpServletRequest request, String message) {
        request.getSession().setAttribute("message", message);
    }

    public static String html(Object value) {
        if (value == null) return "";
        return value.toString().replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }

    public static String selected(Object actual, String expected) {
        return expected.equals(actual) ? " selected" : "";
    }

    public static String field(HttpServletRequest request, String key, Object saved) {
        return html(request.getParameter(key) == null ? saved : request.getParameter(key));
    }
}
