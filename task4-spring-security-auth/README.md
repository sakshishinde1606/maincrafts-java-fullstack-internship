# Task 4 — Login + Role-based Access Control (Spring Security)

Builds on Task 3: the `/contacts` endpoint and the dashboard page are now
protected. Only a logged-in user with role **ADMIN** can view them.

## Project structure
```
task4-spring-security-auth/
├── pom.xml
├── src/main/java/com/maincrafts/task4/
│   ├── Task4Application.java
│   ├── model/Contact.java
│   ├── repository/ContactRepository.java
│   ├── controller/ContactController.java   # POST /submit (public), GET /contacts (ADMIN only)
│   └── config/SecurityConfig.java          # Spring Security rules, BCrypt, in-memory admin user
└── src/main/resources/
    ├── application.properties
    └── static/
        ├── index.html          # public landing page + contact form
        └── contacts.html       # ADMIN-only dashboard, with a logout button
```

## Sample admin credentials (in-memory, for testing)
```
Username: admin
Password: admin@123
```
These are defined in `SecurityConfig.java` via an `InMemoryUserDetailsManager`
with the password BCrypt-hashed, exactly like the task's starter code. For a
production app you'd replace this with a `users` table (username, hashed
password, role) looked up through a `UserDetailsService` backed by JPA —
the `Contact`/`ContactRepository` pattern from Task 2 & 3 is a good template
to copy for a `User`/`UserRepository` if you want to extend this further.

## How access control works
- `/`, `/index.html`, `/login`, and `POST /submit` are public — anyone can
  view the landing page and submit the contact form.
- `/contacts` (the JSON API) and `/contacts.html` (the dashboard) require a
  logged-in user with role `ADMIN`.
- Spring Security's **default login page** is used (at `/login`) for
  simplicity — no custom login HTML needed.
- After logging in, you're redirected straight to `/contacts.html`.
- The **Log out** button on the dashboard posts to `/logout` (Spring
  Security's built-in logout endpoint).

## Setup

1. Create the database:
   ```sql
   CREATE DATABASE task4_db;
   ```
2. Edit `src/main/resources/application.properties` with your MySQL
   username/password.
3. Run:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Try it out:
   - Visit **http://localhost:8080** — submit the contact form (works, no login needed).
   - Visit **http://localhost:8080/contacts.html** directly — you'll be
     bounced to the Spring Security login page.
   - Log in with `admin` / `admin@123` — you land on the dashboard and can
     see all contacts.
   - Click **Log out**, then try `/contacts.html` again — you're sent back
     to the login page.

## What to check off
- [x] `users`-style credentials with hashed password (BCrypt) and a role
- [x] Spring Security configured for login authentication
- [x] `/contacts` protected — only role `ADMIN` can access it
- [x] Passwords hashed with BCrypt (no plain text)
- [x] Login form (Spring Security's default form)
- [x] Redirect to dashboard after login
- [x] Dashboard fetches `/contacts` and renders a table
- [x] Logout button
- [ ] **For your submission:** take screenshots of the login page, an
  unauthorized-access attempt on `/contacts.html`, and the logged-in admin
  dashboard, and include them with this README as required by the task.
