# Universal Ai University - Campus Lost & Found

For Universal Ai University, Karjat, Mumbai, Maharashtra, India.

Java Swing and MySQL project, restructured around Employee Management System, Student Management System, and javaSem5. All application classes are in one package: `campus.lost.and.found.system`.

## Run in NetBeans

1. Choose **File > Open Project** and select `D:\Java\CampusLostAndFoundSystem`.
2. Keep the local MySQL service running. The existing `lostfounddb` database is already connected.
3. Right-click this project and select **Clean and Build**, then **Run**.
4. The main class is `campus.lost.and.found.system.LoginForm`. Log in with your existing campus account and select its USER or ADMIN role.

The JDBC username/password have been copied from the classroom Employee Management System `DBConnect.java` into this project's `DBConnect.java`. The connection is `jdbc:mysql://localhost:3306/lostfounddb`; it does not use the employee or student database. Existing accounts, passwords, reports and claims are preserved.

Java 17 or newer is required. This machine's JDK 25 and NetBeans 25 were used for verification. Only the bundled MySQL Connector/J JAR is required. There is no Maven, web server, FlatLaf or JCalendar dependency.

## How the source matches the classroom examples

| Classroom example | Campus project |
| --- | --- |
| One employee/student package | One `campus.lost.and.found.system` package |
| `DBConnect.dbCon()` and `DriverManager` | `DBConnect.dbCon()` with local credentials |
| `Employee -> EmployeeDetails -> EmployeeContact -> EmployeeOperations` | `Item -> ItemDetails -> ItemContact -> LostItemOperations` |
| Constructors, fields, `super(...)`, inheritance | Same approach for item information and operations |
| `EmployeeOperations` CRUD methods | `LostItemOperations.addItem/searchItem/updateItem/deleteItem` |
| `MainFrame`, `JDesktopPane`, menus, `JInternalFrame` | `MainFrame` opens the campus operation forms |
| `NewEmployee`, `EditEmployee`, `DeleteEmployee`, `SearchEmployee` | `NewLostItem`, `EditLostItem`, `DeleteLostItem`, `SearchLostItem` |
| `PreparedStatement`, `ResultSet`, `try/catch`, `JOptionPane` | Same JDBC and event-handler approach |
| Collection examples in Basics | `ArrayList` holds search results |
| Java files paired with NetBeans `.form` files | All nine windows/forms have matching `.form` files |

The DAO/DTO folders, `ConnectionFactory`, XML credentials and old inventory configuration are removed from active source. NetBeans still uses `build.xml`, `nbproject/*.xml`, and `.form` metadata, just as the classroom projects do. These are IDE/build files; application code does not parse XML. [NetBeans explains the project and GUI Builder format here](https://netbeans.apache.org/tutorial/main/kb/docs/java/quickstart-gui/).

## Source files

All files below are in `src/campus/lost/and/found/system/`.

| File | Purpose |
| --- | --- |
| `DBConnect.java` | MySQL connection; run this file alone to check the connection |
| `Item.java`, `ItemDetails.java`, `ItemContact.java` | Item information using inheritance |
| `LostItemOperations.java` | CRUD, filters, keywords, pagination, claims, handovers |
| `User.java`, `UserOperations.java` | Registration, login, profile, password changes, session logs |
| `LoginForm.java`, `RegisterUser.java` | Login and registration windows |
| `MainFrame.java` | Main window, menus and desktop pane |
| `UserProfile.java` | Profile, show/hide password, change password |
| `NewLostItem.java`, `EditLostItem.java`, `DeleteLostItem.java` | Report operation forms |
| `SearchLostItem.java` | Search, My Reports, and claim/handover history views |
| `ClaimItem.java` | Claims for users and handovers for administrators |

Open a form's Java file and use **Design** to edit its layout. Put button behavior in the `...ActionPerformed` methods outside `initComponents()`. Generated layouts use the guarded sections NetBeans expects. The date/time control is a standard Swing spinner (`yyyy-MM-dd HH:mm`), avoiding an extra calendar library.

## Quick use and storage

Register or log in with the correct USER/ADMIN role. Use **Report a Lost Item** to enter item name, category, pickup location/date/time, finder, contact and description. Use **Search Lost Items** to filter reports and claim an unclaimed item. **My Reports** lets users edit/delete their own unclaimed reports; administrators can manage all reports and record handovers.

All saved data is in local MySQL database `lostfounddb`: `users` stores account/profile fields; `lost_items` stores reports, status, submitter and claim/handover details; `userlogs` stores login/logout times; `app_settings` records whether sample data has been installed. `DBConnect.java` holds the database connection settings. Form fields stay in memory until you save them.

## Features and permissions

- Registration creates a USER account; duplicate usernames and names are rejected.
- Login checks username, password and role. Profile display, password changes and logout logs remain available.
- Reports retain name, category, location/date/time, finder, contact, description and status.
- Search supports combined filters, keywords, newest/oldest sorting, 18-row pages and full details.
- Users edit/delete only their own unclaimed reports, record claims and see their own claim history.
- Administrators edit/delete reports, record handovers, and see all claim/handover history.
- Registered handover recipients are linked to their account; an unregistered recipient ID can still be recorded as before.
- Editing preserves existing claim details. Updates enforce ownership/status checks even if a record changes after opening a form.

## Database setup on another machine

Your current database is ready; there is no need to re-import it.

For a new installation, execute `SQL/LostFoundDB.sql` once in MySQL Workbench, then edit the URL/username/password in `DBConnect.java`. The script creates tables and Indian campus demo records without deleting existing rows. Re-running it does not duplicate sample items. Fresh installations have `admin` / `admin` (ADMIN) and `user` / `user` (USER); existing passwords are not reset.

The 29 sample reports use Indian names and generic university campus areas. Sample +91 contact numbers are demonstration placeholders. Replace them with actual details when creating real reports. Setup is an explicit SQL task; opening a connection does not change tables or sample data.

## Optional command-line launch

Run `compile.cmd`, then `run.cmd`, or use `compile.ps1` and `run.ps1`. After a NetBeans build, you can also run:

```text
java -jar dist/CampusLostAndFoundSystem.jar
```

## Backups and verification

- `work/original-project.zip`: original project before restructuring.
- `work/before-university-localization/`: project and database snapshots before campus localization.
- `work/database-before.sql`: campus database backup before restructuring.
- `work/legacy/`: archived source/configuration/libraries, excluded from the build.
- `work/verification-results.txt`: functional and form-layout checks.
- `work/other-projects-before.json`: hashes for checking other Java projects are unchanged.

The `work` folder is ignored by Git and is not application source. It contains local backups and verification helpers, including credentials; keep it local. The pre-existing modification in `JSP/src/java/AuthenticationServlet.java` was left untouched.

Original application provenance: adapted earlier from [AsjadIqbal/InventoryManagementSystem](https://github.com/AsjadIqbal/InventoryManagementSystem). This refactor keeps the campus behavior while replacing source organization with the classroom pattern.
