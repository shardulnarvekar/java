<% request.setAttribute("title", "Sign in"); %>
<%@include file="/WEB-INF/views/header.jspf"%>
<section class="auth-layout">
    <div class="intro"><p class="eyebrow">A LITTLE HELP GOES A LONG WAY</p><h1>Lost on campus?<br>Let's find it.</h1><p>Report what you found, search campus belongings, and help them reach the right person.</p><div class="steps"><p><b>01</b> Report a found belonging</p><p><b>02</b> Search campus reports</p><p><b>03</b> Claim it and reconnect</p></div></div>
    <div class="panel auth-panel"><p class="eyebrow">WELCOME BACK</p><h2>Sign in to your campus</h2><p class="muted">Use your registered student ID or username.</p>
        <form action="<%= context %>/AuthenticationServlet" method="post" class="stack">
            <input type="hidden" name="token" value="<%= formToken %>">
            <label>Student ID / Username<input name="username" maxlength="20" autocomplete="username" value="<%= WebSupport.field(request, "username", "") %>" required></label>
            <label>Password<input type="password" name="password" maxlength="200" autocomplete="current-password" required></label>
            <label>Sign in as<select name="role"><option value="USER"<%= WebSupport.selected(request.getParameter("role"), "USER") %>>Student / User</option><option value="ADMIN"<%= WebSupport.selected(request.getParameter("role"), "ADMIN") %>>Administrator</option></select></label>
            <button class="button">Sign in</button>
        </form>
        <p class="form-note">New here? <a href="<%= context %>/register.jsp">Create an account</a></p>
    </div>
</section>
<%@include file="/WEB-INF/views/footer.jspf"%>
