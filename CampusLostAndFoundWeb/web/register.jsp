<% request.setAttribute("title", "Create account"); %>
<%@include file="/WEB-INF/views/header.jspf"%>
<section class="narrow"><p class="eyebrow">JOIN YOUR CAMPUS COMMUNITY</p><h1>Create your account</h1><p class="muted">Register as a student to report found items and claim your belongings.</p>
<form class="panel stack" action="<%= context %>/RegisterServlet" method="post">
    <input type="hidden" name="token" value="<%= formToken %>">
    <div class="form-grid">
        <label>Full name<input name="name" maxlength="45" autocomplete="name" value="<%= WebSupport.field(request, "name", "") %>" required></label>
        <label>Student ID / Username<input name="username" maxlength="20" pattern="[A-Za-z0-9_.\-]{1,20}" autocomplete="username" value="<%= WebSupport.field(request, "username", "") %>" required><small>Letters, numbers, dots, hyphens or underscores.</small></label>
        <label>Location / Hostel<input name="location" maxlength="45" value="<%= WebSupport.field(request, "location", "") %>" required></label>
        <label>Phone number<input type="tel" name="phone" maxlength="20" autocomplete="tel" value="<%= WebSupport.field(request, "phone", "") %>" required></label>
        <label>Password<input type="password" name="password" maxlength="200" autocomplete="new-password" required></label>
        <label>Confirm password<input type="password" name="confirmPassword" maxlength="200" autocomplete="new-password" required></label>
    </div>
    <button class="button">Create account</button>
    <p class="form-note">Already registered? <a href="<%= context %>/login.jsp">Sign in</a></p>
</form></section>
<%@include file="/WEB-INF/views/footer.jspf"%>
