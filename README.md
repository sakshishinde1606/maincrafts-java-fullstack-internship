# Java Full Stack Web Development Internship — Maincrafts Technology

All 4 tasks, each as its **own standalone Spring Boot project** you can open
directly in VS Code (or IntelliJ) and run independently.

```
java-fullstack-internship/
├── task1-landing-contact-form/    # Task 1: landing page + contact form, console logging
├── task2-mysql-jpa/               # Task 2: save form data to MySQL via Spring Data JPA
├── task3-contact-dashboard/       # Task 3: dashboard that lists all contacts
└── task4-spring-security-auth/    # Task 4: login + role-based access control
```

Each folder is a complete, independent Maven project with its own `pom.xml`
— open **that specific folder** in VS Code, not the parent folder, when you
want to work on / run one task. Each also has its own `README.md` with
exact run instructions.

## One-time setup (do this first)

You need these installed on your machine:

1. **JDK 17+** — check with `java -version`
2. **Maven** — check with `mvn -version` (or use the included wrapper `./mvnw` if present)
3. **VS Code** with these extensions:
   - Extension Pack for Java
   - Spring Boot Extension Pack
4. **MySQL** (needed from Task 2 onward) — via MySQL Workbench, XAMPP, or Docker.
   You do **not** need MySQL for Task 1.

## Quick start for any task

```bash
cd task1-landing-contact-form      # or task2 / task3 / task4
mvn spring-boot:run
```

Then open **http://localhost:8080** in your browser.

Every project runs on port `8080` by default, so **run one task at a time**
(stop one with `Ctrl+C` before starting another), unless you change the port
in that task's `application.properties`.

## Task overview

| Task | What it adds | Needs MySQL? |
|------|---------------|:---:|
| **1** | Landing page + contact form → Spring Boot controller prints submissions to console | No |
| **2** | Form data persisted to a MySQL `contacts` table via Spring Data JPA + `/contacts` JSON endpoint | Yes |
| **3** | A dashboard page (`contacts.html`) that fetches `/contacts` and renders a table | Yes |
| **4** | Spring Security login; `/contacts` restricted to an `ADMIN` role, BCrypt-hashed password | Yes |

## MySQL setup (Tasks 2–4)

Each task uses its **own database** so they don't collide:
`task2_db`, `task3_db`, `task4_db`. You can create them manually:
```sql
CREATE DATABASE task2_db;
CREATE DATABASE task3_db;
CREATE DATABASE task4_db;
```
or just update the username/password in each task's
`application.properties` — the connection string already includes
`createDatabaseIfNotExist=true`, so if your MySQL user has permission, the
database is created automatically the first time you run the app.

## Task 4 login credentials
```
Username: admin
Password: admin@123
```

## Notes
- All 4 backends are self-contained (no shared code) so each folder can be
  zipped up and submitted independently if needed.
- Frontends live in `src/main/resources/static/` in each project, so Spring
  Boot serves them automatically — no separate frontend server needed.
