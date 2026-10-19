<%-- 
    Document   : home
    Created on : 16 Oct 2026, 2:06:40 pm
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
            String a =(String)session.getAttribute("user");
            out.println("Welcom"+a);
        %>
    </body>
</html>
