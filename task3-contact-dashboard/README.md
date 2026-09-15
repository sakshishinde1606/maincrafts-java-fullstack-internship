# Task 3 — Contact Management Dashboard

Builds on Task 2: the same form saves contacts to MySQL, and now there's a
dedicated **dashboard page** that fetches `/contacts` with JavaScript and
renders every submission in a table.

## Project structure
```
task3-contact-dashboard/
├── pom.xml
├── src/main/java/com/maincrafts/task3/
│   ├── Task3Application.java
│   ├── model/Contact.java
│   ├── repository/ContactRepository.java
│   └── controller/ContactController.java   # POST /submit, GET /contacts
└── src/main/resources/
    ├── application.properties
    └── static/
        ├── index.html          # landing page + contact form
        └── contacts.html       # dashboard — fetches /contacts and renders a table
```

## Setup

1. Create the database (or let it auto-create):
   ```sql
   CREATE DATABASE task3_db;
   ```
2. Edit `src/main/resources/application.properties` with your MySQL
   username/password.
3. Run:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Open **http://localhost:8080** — submit a few contacts.
5. Open **http://localhost:8080/contacts.html** — see them all listed in a table.

## What to check off
- [x] `/contacts` endpoint returns all contacts as JSON
- [x] Dashboard page (`contacts.html`) built with HTML + JS
- [x] Uses `fetch()` to call `/contacts`
- [x] Results rendered in a table
