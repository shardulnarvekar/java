<%@page import="java.util.ArrayList,java.net.URLEncoder,java.nio.charset.StandardCharsets"%>
<% request.setAttribute("title", "Campus reports"); %>
<%@include file="header.jspf"%>
<%
    String view = (String) request.getAttribute("view");
    String heading = "mine".equals(view) ? "My reports" : "history".equals(view) ? "Claim & handover history" : "Campus reports";
    String subtitle = "mine".equals(view) ? "Your found-item reports. Open a report to manage it." : "history".equals(view) ? ("ADMIN".equals(currentUser.userType) ? "All campus claims and handovers." : "Items claimed by you or handed over to your account.") : "Something familiar? Search the belongings found around campus.";
    ArrayList<LostItemOperations> items = (ArrayList<LostItemOperations>) request.getAttribute("items");
    int currentPage = (Integer) request.getAttribute("pageNumber");
    int pageCount = (Integer) request.getAttribute("pages");
    String paging = context + "/SearchServlet?view=" + view;
    for (String key : new String[]{"name", "category", "location", "status", "keyword", "sort"})
        paging += "&" + key + "=" + URLEncoder.encode(WebSupport.text(request, key), StandardCharsets.UTF_8.name());
%>
<div class="page-heading"><div><p class="eyebrow">CAMPUS COMMUNITY / <%= WebSupport.html(currentUser.userType) %></p><h1><%= heading %></h1><p class="muted"><%= subtitle %></p></div><a class="button" href="<%= context %>/ReportServlet">+ Report a found item</a></div>
<form action="<%= context %>/SearchServlet" method="get" class="panel search-panel">
    <input type="hidden" name="view" value="<%= view %>">
    <div class="search-main"><label>Search all details<input name="keyword" placeholder="Item, finder, description..." value="<%= WebSupport.field(request, "keyword", "") %>"></label><button class="button">Search reports</button><a class="button secondary" href="<%= context %>/SearchServlet?view=<%= view %>">Reset</a></div>
    <div class="filter-grid">
        <label>Item name<input name="name" value="<%= WebSupport.field(request, "name", "") %>"></label>
        <label>Category<select name="category"><option>All</option><% for (String c : LostItemOperations.CATEGORIES) { %><option<%= WebSupport.selected(request.getParameter("category"), c) %>><%= WebSupport.html(c) %></option><% } %></select></label>
        <label>Found location<input name="location" value="<%= WebSupport.field(request, "location", "") %>"></label>
        <label>Status<select name="status"><option>All</option><% for (String s : LostItemOperations.STATUSES) { %><option<%= WebSupport.selected(request.getParameter("status"), s) %>><%= s %></option><% } %></select></label>
        <label>Sort by<select name="sort"><option value="newest">Newest found first</option><option value="oldest"<%= WebSupport.selected(request.getParameter("sort"), "oldest") %>>Oldest found first</option></select></label>
    </div>
</form>
<div class="result-caption"><strong><%= request.getAttribute("total") %> reports</strong><span>Page <%= currentPage %> of <%= pageCount %></span></div>
<% if (items.isEmpty()) { %>
<div class="panel empty"><span class="empty-mark">LF</span><h2>No reports to show</h2><p>Try a different search or report a found item to help someone on campus.</p><a href="<%= context %>/ReportServlet">Report a found item &rarr;</a></div>
<% } else { %>
<div class="table-wrap"><table><caption class="sr-only"><%= heading %></caption><thead><tr><th>Report / Item</th><th>Category</th><th>Found at</th><th>Date found</th><th>Status</th><th>Details</th></tr></thead><tbody>
<% for (LostItemOperations item : items) { %>
<tr><td><small>#<%= item.id %></small><strong><%= WebSupport.html(item.itemName) %></strong></td><td><%= WebSupport.html(item.category) %></td><td><%= WebSupport.html(item.pickupLocation) %></td><td class="date-cell"><%= WebSupport.html(item.pickupTime) %></td><td><span class="badge <%= "Unclaimed".equals(item.status) ? "available" : "closed" %>"><%= WebSupport.html(item.status) %></span></td><td><a href="<%= context %>/ItemServlet?id=<%= item.id %>" aria-label="Details for <%= WebSupport.html(item.itemName) %>">View &rarr;</a></td></tr>
<% } %></tbody></table></div>
<% } %>
<nav class="pagination" aria-label="Report pages"><% if (currentPage > 1) { %><a class="button secondary" href="<%= WebSupport.html(paging + "&page=" + (currentPage - 1)) %>">&larr; Previous</a><% } %><% if (currentPage < pageCount) { %><a class="button secondary" href="<%= WebSupport.html(paging + "&page=" + (currentPage + 1)) %>">Next &rarr;</a><% } %></nav>
<%@include file="footer.jspf"%>
