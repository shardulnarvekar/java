<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-store"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login - Campus Item Desk</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h1>Campus Item Desk</h1>
    <p>Lost and found items on campus.</p>
    <h2>Login</h2>
    <%@include file="WEB-INF/message.jspf"%>
    <form action="LoginServlet" method="post">
        <p><label for="username">Username:</label> <input id="username" name="username" autocomplete="username" maxlength="30" required></p>
        <p><label for="password">Password:</label> <input id="password" type="password" name="password" autocomplete="current-password" maxlength="50" required></p>
        <button type="submit">Login</button>
    </form>
</body>
</html>
