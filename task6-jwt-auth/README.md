# Task 6 — JWT-Based Stateless Authentication

Builds on Task 5: replaces the session-based login from Task 4/5 with a
**stateless JWT (JSON Web Token)** system — the pattern used by virtually
every modern REST API and single-page app.

## What changed vs. Task 5

- No more server-side session. Every request must carry its own signed token.
- Login/register now go through `/auth/login` and `/auth/register`, not
  Spring Security's form login.
- Users live in a real database table (`app_users`) instead of being
  hardcoded in `SecurityConfig`.
- Two roles now exist: **ADMIN** (full CRUD) and **USER** (view-only).
- Unauthorized/forbidden requests get clean **JSON** (`{"error": "..."}`),
  not an HTML redirect page.

> **Note on the frontend:** the official Task 6 brief describes a React +
> Axios frontend. To keep this project consistent with Tasks 1–5 (plain
> HTML/JS, zero build step, runs by just opening the folder), the same
> behavior is implemented in vanilla JavaScript instead:
> - `js/api.js` → `apiFetch()` is the Axios-interceptor equivalent: it reads
>   the JWT from `localStorage` and attaches `Authorization: Bearer <token>`
>   to every request automatically, and auto-redirects to the login page on
>   a 401.
> - `requireAuth()` in the same file is the route guard.
>
> If your evaluator specifically requires React, the backend here already
> exposes the exact same `/auth/login`, `/auth/register`, and `/contacts`
> API — you can point a separate React app at it using the Axios snippet
> from the task brief with no backend changes needed.

## Project structure
```
task6-jwt-auth/
├── pom.xml                              # + jjwt-api, jjwt-impl, jjwt-jackson
├── src/main/java/com/maincrafts/contactapp/
│   ├── ContactAppApplication.java
│   ├── model/, repository/, dto/, exception/   # same as Task 5
│   ├── controller/ContactController.java       # same CRUD endpoints as Task 5
│   ├── user/
│   │   ├── User.java                    # username, hashed password, role
│   │   ├── UserRepository.java
│   │   ├── CustomUserDetailsService.java  # loads users from the DB
│   │   └── DataSeeder.java              # seeds the default admin on first run
│   ├── security/
│   │   ├── JwtService.java              # signs & validates tokens
│   │   ├── JwtAuthFilter.java           # reads the Authorization header on every request
│   │   ├── JwtAuthEntryPoint.java       # JSON 401 response
│   │   └── JwtAccessDeniedHandler.java  # JSON 403 response
│   ├── auth/
│   │   ├── AuthController.java          # POST /auth/register, POST /auth/login
│   │   ├── AuthService.java
│   │   ├── RegisterRequest.java / LoginRequest.java / AuthResponse.java
│   └── config/SecurityConfig.java       # stateless, role-based rules
└── src/main/resources/
    ├── application.properties
    └── static/
        ├── index.html        # public landing page + contact form
        ├── login.html        # calls POST /auth/login, stores JWT
        ├── register.html     # calls POST /auth/register
        ├── contacts.html     # dashboard - ADMIN gets full CRUD, USER gets view-only
        └── js/api.js         # apiFetch() + requireAuth() (Axios-interceptor equivalent)
```

## API endpoints

| Method | Path | Access | Purpose |
|---|---|---|---|
| POST | `/submit` | Public | Visitor submits the contact form |
| POST | `/auth/register` | Public | Create a new account (role USER) |
| POST | `/auth/login` | Public | Returns a signed JWT + role |
| GET | `/contacts` | ADMIN or USER | Paginated, sorted list |
| POST / PUT / DELETE | `/contacts...` | ADMIN only | Manage contacts |

## Default accounts

A default **ADMIN** account is automatically created in the database the
first time you run the app (see `DataSeeder.java`):
```
Username: admin
Password: admin@123
```
Anyone can also self-register a **USER** account from `/register.html` —
new accounts always get the USER role (view-only).

## How to run

1. Create the database:
   ```sql
   CREATE DATABASE contactapp_task6_db;
   ```
2. Edit `src/main/resources/application.properties` with your MySQL username/password.
3. Run:
   ```bash
   mvn spring-boot:run
   ```
   On first startup, check the console — you should see:
   ```
   Seeded default ADMIN user -> username: admin | password: admin@123
   ```
4. Open **http://localhost:8080** — submit a message from the public form.
5. Open **http://localhost:8080/login.html**
   - Log in as `admin` / `admin@123` → you can add/edit/delete contacts
   - Or click **Register**, create a new account → log in with it → you
     can only **view** contacts (no Add/Edit/Delete buttons shown, and the
     backend also rejects those requests with a 403 even if attempted directly)
6. Click **Log out** to clear the token and return to the login page.

### Testing with Postman (optional, matches the task brief)
1. `POST http://localhost:8080/auth/login` with JSON body
   `{"username":"admin","password":"admin@123"}` → copy the `token` from the response
2. On any `/contacts` request, add header `Authorization: Bearer <token>`
3. Remove the header or use an expired/invalid token → you should get a
   clean `401 {"error": "..."}` JSON response, not an HTML page

## What to check off
- [x] JJWT dependency added, `JwtService` generates/validates tokens
- [x] `JwtAuthFilter` reads the Authorization header on every request
- [x] `SecurityConfig` is stateless and permits `/auth/**`
- [x] `POST /auth/register` hashes the password with BCrypt and saves to the DB
- [x] `POST /auth/login` authenticates and returns a signed JWT
- [x] ADMIN can manage contacts; USER gets 403 on write operations
- [x] Custom JSON 401/403 responses (no HTML redirect)
- [x] Login page stores the JWT in `localStorage`
- [x] `apiFetch()` attaches the Bearer token to every outgoing request automatically
- [x] Route guard redirects unauthenticated users to the login page
