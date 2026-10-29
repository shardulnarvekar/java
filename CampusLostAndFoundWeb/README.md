# Campus Lost and Found - Web Application

A separate web version of `D:\Java\CampusLostAndFoundSystem`, for Universal Ai University. This is an ordinary **NetBeans Java Web / Ant project**, based on the web examples in `DemoServlet`, `JSP`, and `ShoppingCart`.

## Launch in NetBeans on this computer

1. Open Apache NetBeans IDE 25.
2. Select **File > Open Project** and choose `D:\Java\CampusLostAndFoundWeb`.
3. Keep the **MySQL80** service running. The separate database `campuslostfoundwebdb` has already been created on this computer, with 29 campus sample reports.
4. Right-click **CampusLostAndFoundWeb** in the Projects pane and select **Clean and Build**.
5. Right-click **CampusLostAndFoundWeb** and select **Run**. Select the registered **Apache Tomcat 10.1** server if NetBeans asks.
6. The browser should open `http://localhost:8080/CampusLostAndFoundWeb/`. If your Tomcat uses another port, use that port instead.

If NetBeans displays **Authentication Required - Tomcat Manager Application**, confirm your local Tomcat Manager credentials. This is the server's deployment account, separate from the student/admin logins below. This computer's prompt already has its saved username/password filled in.

Use the project's own **Run** command: another existing project may still be configured as the IDE's main project.

| Demo login | Password | Select this role |
| --- | --- | --- |
| `user` | `user` | Student / User |
| `admin` | `admin` | Administrator |

You can also create a new student account. Existing accounts from the desktop application's database are separate; register again in the web application if needed.

The project is configured for this machine's existing NetBeans 25, JDK 25, `C:\tomcat10`, and registered Tomcat server. Source/target level is Java 11, matching Sir's web examples. Tomcat 10.1 uses `jakarta.servlet`, as do the existing classroom servlets.

## What you can do

- Register, sign in with a role, view your profile, change your password, and sign out.
- Submit found-item reports with category, location, date/time, finder, contact, and description.
- Search by item name, category, location, status, and keyword; sort oldest/newest; browse 18 reports per page.
- Open full report details and view My Reports.
- Edit or delete your own unclaimed reports. Deletion uses a confirmation page.
- Record a claim and view your claim history. Claiming immediately changes the status, following the original desktop project.
- As an administrator, edit/delete any report, record a handover, and view all claims/handovers. Handover recipients with an existing username are linked to their account; other recipient IDs are stored as entered.

Date/time fields use campus local time in India. Editing a report preserves its existing claim information and status.

## How this follows Sir's examples

| Existing classroom source | Technique used here |
| --- | --- |
| `JSP/web/login.jsp` | JSP pages with ordinary HTML forms and Java scriptlets |
| `JSP/src/java/AuthenticationServlet.java` | `request.getParameter`, `HttpSession`, session attributes, `sendRedirect`, and `RequestDispatcher.forward` |
| `DemoServlet/src/java/LoginServlet.java`, `DBConnect.java` | Servlet request handling and JDBC / MySQL connections |
| `ShoppingCart/src/java/AddToCartServlet.java` | `@WebServlet`, `doPost`, session values, and `ArrayList` |
| `ShoppingCart/src/java/ViewCartServlet.java` | Loops that display Java objects as HTML table rows |
| Original Campus Lost and Found project | Item inheritance, user operations, prepared statements, report CRUD, search, claims, and handovers |
| `JSP/nbproject`, `JSP/build.xml` | NetBeans Ant web-project structure and WAR packaging |

There is no Spring, Hibernate, React, Maven, REST API, JavaScript framework, external CSS library, or cloud service. The pages use ordinary HTML and CSS. A small `WebSupport` class shares input validation, HTML escaping, session checks, and a hidden session form token between the servlets and JSP pages.

## Source guide

Application classes are in `src/java/campus/lost/and/found/web/`.

| File | Responsibility |
| --- | --- |
| `DBConnect.java` | Connection URL and local MySQL credentials |
| `Item.java`, `ItemDetails.java`, `ItemContact.java` | The original project's item inheritance chain |
| `LostItemOperations.java` | JDBC report CRUD, combined search, claims, handovers |
| `User.java`, `UserOperations.java` | Accounts, login, password changes, logout logs |
| `AuthenticationServlet.java`, `RegisterServlet.java`, `LogoutServlet.java` | Account forms and sessions |
| `SearchServlet.java`, `ItemServlet.java` | Read reports and forward results to JSP pages |
| `ReportServlet.java` | Add, edit, and delete report forms |
| `ClaimServlet.java` | Claims and administrator handovers |
| `ProfileServlet.java` | Profile display and password change |
| `WebSupport.java` | Shared request/session/form methods |

`web/login.jsp` and `web/register.jsp` are public pages. Pages in `web/WEB-INF/views/` receive data from servlets; users open the servlet URLs instead of accessing these JSP files directly. `header.jspf` and `footer.jspf` share the page layout. `web/css/style.css` is the only stylesheet.

Example request flow: **login.jsp form -> AuthenticationServlet -> UserOperations.login -> DBConnect -> session -> SearchServlet -> reports.jsp**.

## Database and dependencies

The web application only uses **`campuslostfoundwebdb`**, with `users`, `lost_items`, `userlogs`, and `app_settings` tables. The original `lostfounddb`, employee/student databases, and all existing project files are separate.

The MySQL username and password in this new project's `DBConnect.java` follow the original desktop project's local connection. The classroom password-comparison approach is retained for this local lab application. Default demo passwords are shown above. The bundled MySQL Connector/J JAR is under `web/WEB-INF/lib/`, and NetBeans includes it in the WAR. No library download is needed on this computer.

On another computer:

1. Install/register Tomcat **10.1** in NetBeans and use JDK 11 or later.
2. Run `SQL/CampusLostFoundWebDB.sql` in MySQL Workbench. This creates the separate web database; it does not use `lostfounddb`. Do not rename the database to an existing project's database.
3. Update the URL, username, and password in this project's `DBConnect.java`.
4. In Project Properties > Run, choose the local Tomcat server and use context path `/CampusLostAndFoundWeb`.
5. Clean and Build, then Run.

Database setup is explicit. Opening a JDBC connection never creates tables or imports samples. The SQL file can be rerun without resetting existing accounts or duplicating the seeded sample reports.

## Troubleshooting

- **Missing server / unresolved server**: Project Properties > Run > choose the registered Apache Tomcat 10.1 server. Use Tomcat 10.1 because the imports are `jakarta.servlet`, matching Sir's examples.
- **Database connection error**: check MySQL80, the database name, and this project's `DBConnect.java`. On another machine, execute the SQL setup first.
- **Access denied for MySQL**: update only this new project's JDBC credentials.
- **Address already in use**: use NetBeans Services > Servers to check the configured Tomcat server; adjust its port if necessary.
- **Wrong project opens when pressing F6**: right-click CampusLostAndFoundWeb and choose Run directly.
- **Expired form**: reopen the page and submit again. Sessions expire after 30 minutes of inactivity.

## Verification

The application was built both with the installed NetBeans Ant runtime and with NetBeans IDE's own **Clean and Build**, which reported **BUILD SUCCESSFUL**. It was tested against Tomcat 10.1.57 and MySQL 8. Tests covered account registration/login, search/pagination, report CRUD, ownership rules, claims, administrator handovers, password changes, logout logs, invalid forms, and HTML escaping. Temporary test accounts/reports were removed. Browser layouts were checked at desktop and mobile sizes.

Before/after checks confirmed 711 pre-existing files and the original desktop database rows were unchanged. See `docs/verification.txt` and `docs/screenshots/` for results and previews.

Tomcat's standard WAR layout is documented at https://tomcat.apache.org/tomcat-10.1-doc/appdev/deployment.html.
