<%-- 
    Document   : login
    Created on : 16 Oct 2026, 2:03:55 pm
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
        <form action="AuthenticationServlet" method="POST">
            Enter Username: <input type="text" name="t1"><br>
            Enter Password: <input type="text" name="t2"><br>
            <input type="submit" value="login">
        </form>
        <%
            String a =(String)session.getAttribute("message");
            if(a!=null){
                out.println(a);
                session.invalidate();
            }
        %>
    </body>
</html>
