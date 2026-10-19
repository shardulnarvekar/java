<%-- 
    Document   : arithm
    Created on : 15 Oct 2026, 12:02:51 pm
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
        <h1>Hello World</h1>
        <form action="arithm.jsp" method="GET">
            First No: <input type="text" name="t1"><br>
            Second No: <input type="text" name="t2"><br>
            <input type="submit" value="Add">
        </form>
        <%
            String a = request.getParameter("t1");
            String b = request.getParameter("t2");

            if (a != null && b != null) {
                int num1 = Integer.parseInt(a);
                int num2 = Integer.parseInt(b);

                int sum = num1 + num2;

                out.println("Addition = " + sum);
            }
        %>
    </body>
</html>
