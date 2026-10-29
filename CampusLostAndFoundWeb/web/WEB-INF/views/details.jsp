<% request.setAttribute("title", "Report details"); %>
<%@include file="header.jspf"%>
<% LostItemOperations item = (LostItemOperations) request.getAttribute("item"); %>
<a class="back-link" href="<%= context %>/SearchServlet">&larr; Back to reports</a>
<div class="page-heading"><div><p class="eyebrow">FOUND ITEM / REPORT #<%= item.id %></p><h1><%= WebSupport.html(item.itemName) %></h1><p class="muted"><%= WebSupport.html(item.category) %></p></div><span class="badge <%= item.canClaim() ? "available" : "closed" %>"><%= WebSupport.html(item.status) %></span></div>
<div class="detail-layout"><section class="panel"><h2>Found-item details</h2><dl class="details">
    <dt>Found at</dt><dd><%= WebSupport.html(item.pickupLocation) %></dd>
    <dt>Date and time</dt><dd><%= WebSupport.html(item.pickupTime) %></dd>
    <dt>Found by</dt><dd><%= WebSupport.html(item.pickupPerson) %></dd>
    <dt>Contact</dt><dd><%= WebSupport.html(item.contact) %></dd>
    <dt>Submitted by</dt><dd><%= WebSupport.html(item.submitterUsername) %></dd>
</dl><h3>Description</h3><p class="description"><%= item.description == null || item.description.isEmpty() ? "No extra description provided." : WebSupport.html(item.description) %></p>
<% if (item.canEdit(currentUser)) { %><div class="actions"><a class="button secondary" href="<%= context %>/ReportServlet?id=<%= item.id %>">Edit report</a><a class="danger-link" href="<%= context %>/ReportServlet?action=delete&amp;id=<%= item.id %>">Delete report</a></div><% } %>
</section>
<aside>
<% if (item.canClaim()) { %>
<section class="panel"><p class="eyebrow">DOES THIS BELONG TO YOU?</p><h2>Claim this item</h2><p class="muted">Contact the finder to identify your belonging and arrange collection. Submitting records a claim immediately.</p>
<form action="<%= context %>/ClaimServlet" method="post" class="stack"><input type="hidden" name="token" value="<%= formToken %>"><input type="hidden" name="action" value="claim"><input type="hidden" name="id" value="<%= item.id %>">
    <label>Claimant name<input name="claimant" maxlength="50" value="<%= WebSupport.field(request, "claimant", currentUser.name) %>" required></label>
    <label>Contact number<input type="tel" name="claimContact" maxlength="50" value="<%= WebSupport.field(request, "claimContact", currentUser.phone) %>" required></label>
    <button class="button">Record my claim</button>
</form></section>
<% if ("ADMIN".equals(currentUser.userType)) { %>
<section class="panel"><p class="eyebrow">ADMINISTRATOR</p><h2>Record a handover</h2><p class="muted">Enter the recipient's username / student ID. Registered recipients are linked to their account.</p><form action="<%= context %>/ClaimServlet" method="post" class="stack"><input type="hidden" name="token" value="<%= formToken %>"><input type="hidden" name="action" value="handover"><input type="hidden" name="id" value="<%= item.id %>"><label>Recipient ID / Username<input name="recipient" maxlength="50" value="<%= WebSupport.field(request, "recipient", "") %>" required></label><button class="button secondary">Record handover</button></form></section>
<% } } else { %>
<section class="panel"><p class="eyebrow">CLAIM RECORD</p><h2><%= WebSupport.html(item.status) %></h2><dl class="details"><dt>Recipient</dt><dd><%= WebSupport.html(item.claimant) %></dd><dt>Contact</dt><dd><%= WebSupport.html(item.claimContact) %></dd><dt>Account</dt><dd><%= WebSupport.html(item.claimantUsername) %></dd><dt>Recorded at</dt><dd><%= WebSupport.html(item.claimTime) %></dd></dl></section>
<% } %>
</aside></div>
<%@include file="footer.jspf"%>
