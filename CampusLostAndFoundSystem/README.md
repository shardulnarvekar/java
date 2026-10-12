# Campus Lost & Found System

A Java Swing desktop application for reporting, searching, claiming, and handing over lost items on campus. It uses JDBC and MySQL, with the existing DTO, DAO, database, and UI structure. The project builds with JDK 17 through NetBeans/Ant or the included Windows scripts.

The project was adapted from [AsjadIqbal/InventoryManagementSystem](https://github.com/AsjadIqbal/InventoryManagementSystem) for a Java object oriented programming course.

## Features

- User registration and role based login (`USER` and `ADMIN`)
- Lost item reports with category, location, date, finder, contact, and description
- Combined filters, keyword search, sorting, pagination, and item details
- User editing and deletion of their own unclaimed reports
- User claims and personal claim history
- Administrator editing, deletion, handover, and complete claim history
- MySQL persistence with English categories and statuses: `Unclaimed`, `Claimed`, and `Handed Over`

## Requirements

- JDK 17 or newer
- MySQL 8
- Bundled JARs in `lib`

The application creates missing tables and adds sample items only when the lost item table has never been initialized. It does not delete reports during startup. Existing default account details are preserved.

## Database setup

For a new database, run `SQL/LostFoundDB.sql` in MySQL. This script uses `CREATE TABLE IF NOT EXISTS` and does not delete existing data. The application can also create its tables after the `LostFoundDB` database itself exists.

For an existing Chinese language installation, run `SQL/migrate_to_english.sql` once after backing up the database. The migration is safe to rerun: it updates the three status values, category values, and unchanged bundled sample records in place. User created item details and account changes are left alone. Startup also applies the same legacy status and category conversions automatically.

Default connection settings:

```text
LOSTFOUND_DB_URL=jdbc:mysql://localhost:3306/LostFoundDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai&characterEncoding=utf8
LOSTFOUND_DB_USER=root
LOSTFOUND_DB_PASSWORD=root
```

Override these environment variables or use matching JVM system properties for your MySQL setup. For example, in Windows CMD:

```cmd
set LOSTFOUND_DB_PASSWORD=your_password
```

Default demo accounts, if they do not already exist:

| Role | Username | Password |
| --- | --- | --- |
| Administrator | `admin` | `admin` |
| User | `user` | `user` |

Change the demo passwords before using the application with real data.

## Build and run on Windows CMD

```cmd
compile.cmd
run.cmd
```

The PowerShell scripts `compile.ps1` and `run.ps1` are also available. In NetBeans, open this directory as an Ant project and use **Clean and Build**. No Maven or servlet container is required.

## Project layout

| Path | Purpose |
| --- | --- |
| `src/com/campus/lostandfound/UI` | Swing login, dashboard, forms, search, and history |
| `src/com/campus/lostandfound/DAO` | JDBC operations and permissions |
| `src/com/campus/lostandfound/DTO` | User and item data objects |
| `src/com/campus/lostandfound/Database` | Connection, schema initialization, and sample data |
| `SQL/LostFoundDB.sql` | Safe database initialization |
| `SQL/migrate_to_english.sql` | In place migration of legacy values |

Screenshots in `docs/report-images` document an earlier version of the interface.
