<% request.setAttribute("title", "Delete report"); %>
<%@include file="header.jspf"%>
<% LostItemOperations item = (LostItemOperations) request.getAttribute("item"); %>
<section class="narrow panel"><p class="eyebrow">REPORT #<%= item.id %></p><h1>Delete this report?</h1><p>You are deleting <strong><%= WebSupport.html(item.itemName) %></strong>. This removes the report and any associated claim details.</p><form action="<%= context %>/ReportServlet" method="post" class="actions"><input type="hidden" name="token" value="<%= formToken %>"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%= item.id %>"><button class="button danger">Yes, delete report</button><a class="button secondary" href="<%= context %>/ItemServlet?id=<%= item.id %>">Keep report</a></form></section>
<%@include file="footer.jspf"%>
