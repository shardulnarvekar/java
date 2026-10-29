<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-store");
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add Item - Campus Item Desk</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <%@include file="WEB-INF/navigation.jspf"%>
    <h2>Add Found Item</h2>
    <p>Enter the details of an item found on campus. Only the description is optional.</p>
    <%@include file="WEB-INF/message.jspf"%>
    <form action="AddItemServlet" method="post">
        <p><label for="itemName">Item name:</label> <input id="itemName" name="itemName" maxlength="100" required></p>
        <p><label for="location">Found at:</label> <input id="location" name="location" maxlength="100" required></p>
        <p><label for="foundDate">Date found:</label> <input id="foundDate" type="date" name="foundDate" required></p>
        <p><label for="contact">Contact:</label> <input id="contact" name="contact" maxlength="50" required></p>
        <p><label for="description">Description:</label> <textarea id="description" name="description" rows="3" maxlength="500"></textarea></p>
        <button type="submit">Save Item</button>
        <a href="items.jsp">Cancel</a>
    </form>
</body>
</html>
