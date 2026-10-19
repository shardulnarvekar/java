<%-- 
    Document   : first.jsp
    Created on : 16 Oct 2026, 1:42:38 pm
    Author     : Shardul
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            String x="Shardul Narvekar";
            session.setAttribute("y", x);//we are making object y and assigning the value of x
            response.sendRedirect("second.jsp");
        %>
    </body>
</html>
