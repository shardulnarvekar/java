<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*,campusitemdesk.DBConnect"%>
<%
    response.setHeader("Cache-Control", "no-store");
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%!
    // Display typed text as text, even when it contains HTML characters.
    String html(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Items - Campus Item Desk</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <%@include file="WEB-INF/navigation.jspf"%>
    <h2>Found Items</h2>
    <%@include file="WEB-INF/message.jspf"%>
    <% String search = request.getParameter("search");
       if (search == null) search = ""; %>
    <form action="items.jsp" method="get">
        <label for="search">Item name:</label>
        <input id="search" name="search" value="<%= html(search) %>" maxlength="100">
        <button type="submit">Search</button>
        <a href="items.jsp">Show All</a>
    </form>
    <p>For an available item, enter the recipient's name and click Mark Returned.</p>
    <div class="table-area">
    <table>
        <tr><th>ID</th><th>Item</th><th>Found at</th><th>Date</th><th>Contact</th><th>Description</th><th>Status</th><th>Return details</th></tr>
        <%
            String sql = "SELECT * FROM items WHERE item_name LIKE ? ORDER BY id DESC";
            try (Connection con = DBConnect.dbCon();
                    PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, "%" + search + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    boolean found = false;
                    while (rs.next()) {
                        found = true;
        %>
        <tr>
            <td><%= rs.getInt("id") %></td>
            <td><%= html(rs.getString("item_name")) %></td>
            <td><%= html(rs.getString("location")) %></td>
            <td><%= rs.getDate("found_date") %></td>
            <td><%= html(rs.getString("contact")) %></td>
            <td><%= html(rs.getString("description")) %></td>
            <td><%= html(rs.getString("status")) %></td>
            <td>
                <% if ("Available".equals(rs.getString("status"))) { %>
                <form action="ReturnItemServlet" method="post" class="return-form">
                    <input type="hidden" name="id" value="<%= rs.getInt("id") %>">
                    <label for="returnedTo<%= rs.getInt("id") %>">Returned to:</label>
                    <input id="returnedTo<%= rs.getInt("id") %>" name="returnedTo" maxlength="100" required>
                    <button type="submit">Mark Returned</button>
                </form>
                <% } else { %>
                    Returned to: <%= html(rs.getString("returned_to")) %>
                <% } %>
            </td>
        </tr>
        <%
                    }
                    if (!found) {
        %>
        <tr><td colspan="8">No items found.</td></tr>
        <%
                    }
                }
            } catch (SQLException e) {
                application.log("Could not load items", e);
        %>
        <tr><td colspan="8">Could not load items. Check MySQL and DBConnect.java.</td></tr>
        <%
            }
        %>
    </table>
    </div>
</body>
</html>
