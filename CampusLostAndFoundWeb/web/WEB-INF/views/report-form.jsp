<% request.setAttribute("title", "Report a found item"); %>
<%@include file="header.jspf"%>
<%
    LostItemOperations item = (LostItemOperations) request.getAttribute("item");
    boolean editing = item != null;
    String foundTime = editing ? item.pickupTime.toLocalDateTime().toString().substring(0, 16) : "";
%>
<section class="narrow"><a class="back-link" href="<%= context %>/SearchServlet">&larr; Back to reports</a><p class="eyebrow">BRING A BELONGING HOME</p><h1><%= editing ? "Edit report #" + item.id : "Report a found item" %></h1><p class="muted">Record what was found and where. Required fields are marked with *.</p>
<form action="<%= context %>/ReportServlet" method="post" class="panel stack">
    <input type="hidden" name="token" value="<%= formToken %>">
    <input type="hidden" name="action" value="<%= editing ? "edit" : "add" %>">
    <% if (editing) { %><input type="hidden" name="id" value="<%= item.id %>"><% } %>
    <div class="form-grid">
        <label>Item name *<input name="itemName" maxlength="100" value="<%= WebSupport.field(request, "itemName", editing ? item.itemName : "") %>" required></label>
        <label>Category *<select name="category" required><option value="">Choose a category</option><% for (String category : LostItemOperations.CATEGORIES) { %><option<%= WebSupport.selected(request.getParameter("category") == null ? (editing ? item.category : "") : request.getParameter("category"), category) %>><%= WebSupport.html(category) %></option><% } %></select></label>
        <label>Found location *<input name="pickupLocation" maxlength="100" value="<%= WebSupport.field(request, "pickupLocation", editing ? item.pickupLocation : "") %>" required></label>
        <label>Date and time found *<input type="datetime-local" name="pickupTime" value="<%= WebSupport.field(request, "pickupTime", foundTime) %>" required><small>Campus local time (India).</small></label>
        <label>Finder's name *<input name="pickupPerson" maxlength="50" value="<%= WebSupport.field(request, "pickupPerson", editing ? item.pickupPerson : currentUser.name) %>" required></label>
        <label>Contact number *<input type="tel" name="contact" maxlength="50" value="<%= WebSupport.field(request, "contact", editing ? item.contact : currentUser.phone) %>" required></label>
    </div>
    <label>Description<textarea name="description" rows="5" maxlength="2000" placeholder="Colour, brand, identifying marks or other useful details"><%= WebSupport.field(request, "description", editing ? item.description : "") %></textarea></label>
    <div class="actions"><button class="button"><%= editing ? "Save changes" : "Submit report" %></button><a class="button secondary" href="<%= context %>/<%= editing ? "ItemServlet?id=" + item.id : "SearchServlet" %>">Cancel</a></div>
</form></section>
<%@include file="footer.jspf"%>
