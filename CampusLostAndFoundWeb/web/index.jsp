<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% response.sendRedirect(request.getContextPath() + (session.getAttribute("user") == null ? "/login.jsp" : "/SearchServlet")); %>
