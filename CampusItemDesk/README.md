# Campus Item Desk

A small campus lost-and-found web application, using the same basic JSP, Servlet, session, JDBC, MySQL, and NetBeans Ant approach as the examples in `D:\Java`.

## Run in NetBeans

1. Choose **File > Open Project** and select `D:\Java\CampusItemDesk`.
2. Keep the **MySQL80** service running. This computer already has the separate `campusitemdeskdb` database with three sample items.
3. Right-click **CampusItemDesk > Clean and Build**.
4. Right-click **CampusItemDesk > Run**. Use the existing **Tomcat 10.1** server.
5. Open `http://localhost:8080/CampusItemDesk/` if the browser does not open automatically.

**Username:** `campus`  
**Password:** `1234`

This is one shared lab login, checked in `LoginServlet.java`, like the fixed login in Sir's `JSP/src/java/AuthenticationServlet.java`. There are no individual accounts or roles. Every logged-in user can add items and mark available items as returned.

If NetBeans requests Tomcat Manager authentication, confirm your own saved server credentials. That is separate from the application's `campus` login.

## Features

- Login and logout.
- Add a found item: name, location, date, contact, and optional description.
- Show the items in one HTML table.
- Search by item name.
- Record who received an item and change its status from Available to Returned.

There is no registration, profile page, administrator role, edit/delete screen, category filtering, pagination, or separate claim workflow. Items stay in the table after being returned.

## Files to understand

All Java files are in `src/java/campusitemdesk/`.

| File | What it does |
| --- | --- |
| `DBConnect.java` | Opens a MySQL connection with DriverManager. |
| `LoginServlet.java` | Checks the fixed login and stores `user` in the session. |
| `AddItemServlet.java` | Reads the form and uses an INSERT prepared statement. |
| `ReturnItemServlet.java` | Uses an UPDATE prepared statement to record a return. |
| `LogoutServlet.java` | Invalidates the session. |

The three main pages in `web/` are `login.jsp`, `add-item.jsp`, and `items.jsp`. `index.jsp` redirects to the correct starting page. `items.jsp` runs a SELECT query and prints the ResultSet rows into a table using a Java loop.

Two small include files in `web/WEB-INF/` share the navigation and messages. `style.css` contains 19 lines of ordinary CSS for readable forms and table borders. There are no JavaScript files, UI libraries, frameworks, animations, or external fonts.

The application has about **381 lines of Java, JSP, JSP includes, and CSS**. NetBeans' `nbproject/` and build XML files are generated IDE infrastructure, like the classroom projects.

## Suggested build order for your team

1. Create a Java Web Application in NetBeans using Tomcat 10.1. Make the `campusitemdesk` package.
2. Create the database/table using `SQL/CampusItemDeskDB.sql` and add the bundled MySQL JAR to Libraries.
3. Write `DBConnect`, then the fixed login and session/logout code.
4. Make the add-item form and its INSERT servlet.
5. Make the SELECT/ResultSet table and add the name search.
6. Add the recipient field and UPDATE servlet for returning an item.
7. Apply the short stylesheet and try the full flow together.

For example, one teammate can handle login/session pages, one can handle the database/add form, and one can handle the item list/search/return flow. Each part uses the classroom examples directly.

## Database

Only the new **`campusitemdeskdb`** database is used. It contains one table, **`items`**:

`id, item_name, location, found_date, contact, description, status, returned_to`

There is no users table. The database username and password are the existing local connection settings, copied into this project's `DBConnect.java`. The shared application login above is separate from that MySQL connection.

For another computer, execute `SQL/CampusItemDeskDB.sql`, update `DBConnect.java` with that computer's MySQL connection, and select its local Tomcat 10.1 server in Project Properties > Run. Use JDK 11 or later. The MySQL driver is bundled in `web/WEB-INF/lib/`.

The earlier `CampusLostAndFoundWeb` project remains available separately. This project does not share its database or modify its files.

## Verification

Build and browser/HTTP checks covered login, session access, adding items, name search, returning items, repeated return submissions, input validation, and logout. The temporary test item was removed. All 792 pre-existing files and both earlier lost-and-found databases were verified unchanged. See `docs/verification.txt`.
