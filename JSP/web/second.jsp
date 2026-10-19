<%-- 
    Document   : second.jsp
    Created on : 16 Oct 2026, 1:42:49 pm
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
        <h1>Hello World!</h1>This is second.jsp file...
        <br><br>
        <%
            String a =(String)session.getAttribute("y");
            out.println("Data Received:"+a);
        %>
    </body>
</html>

