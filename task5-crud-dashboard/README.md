# Task 5 — Full CRUD Contact Management System

Builds on Task 4: the admin dashboard now supports full **Create, Read,
Update, Delete**, with **pagination, sorting, server-side validation, and
clean error messages** — the same pattern real production CRUD dashboards use.

## Project structure
```
task5-crud-dashboard/
├── pom.xml
├── src/main/java/com/maincrafts/contactapp/
│   ├── ContactAppApplication.java
│   ├── model/Contact.java              # added: status, createdAt fields
│   ├── repository/ContactRepository.java
│   ├── dto/ContactRequest.java         # validated request body (@NotBlank, @Email)
│   ├── exception/
│   │   ├── ResourceNotFoundException.java
│   │   └── GlobalExceptionHandler.java # turns errors into clean JSON
│   ├── controller/ContactController.java   # full CRUD + pagination/sorting
│   └── config/SecurityConfig.java      # same login system as Task 4
└── src/main/resources/
    ├── application.properties
    └── static/
        ├── index.html        # public landing page + contact form
        └── contacts.html     # admin dashboard: table, add/edit modal, pagination, sorting
```

## API endpoints

| Method | Path | Access | Purpose |
|---|---|---|---|
| POST | `/submit` | Public | Visitor submits the contact form |
| GET | `/contacts?page=0&size=10&sort=name,asc` | ADMIN | Paginated, sorted list |
| POST | `/contacts` | ADMIN | Admin manually adds a contact |
| PUT | `/contacts/{id}` | ADMIN | Edit a contact |
| DELETE | `/contacts/{id}` | ADMIN | Delete a contact |

## How to run

1. Create the database:
   ```sql
   CREATE DATABASE contactapp_task5_db;
   ```
2. Edit `src/main/resources/application.properties` with your MySQL username/password.
3. Run:
   ```bash
   mvn spring-boot:run
   ```
4. Open **http://localhost:8080** — submit a message from the public form.
5. Open **http://localhost:8080/contacts.html** — log in with:
   ```
   Username: admin
   Password: admin@123
   ```
6. On the dashboard you can:
   - **Add Contact** — opens a form; try submitting it empty or with a bad email to see live validation errors
   - **Edit** any row — update name/email/message/status
   - **Delete** any row — asks for confirmation first
   - Click any **column header** (Name, Email, Status, Created) to sort by it — click again to reverse direction
   - Use **Prev / Next** at the bottom to page through results

## What to check off
- [x] `POST /contacts`, `GET /contacts`, `PUT /contacts/{id}`, `DELETE /contacts/{id}`
- [x] Bean Validation (`@NotBlank`, `@Email`) on create/edit
- [x] Pagination & sorting via `?page=&size=&sort=`
- [x] Only ADMIN can manage contacts
- [x] Global exception handler — no raw 500 errors, clean JSON instead
- [x] Table view, create/edit form with live error display, pagination buttons, delete confirmation
