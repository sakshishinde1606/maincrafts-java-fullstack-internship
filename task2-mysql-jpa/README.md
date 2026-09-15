# Task 2 — Store Contact Form Data in MySQL (Spring Data JPA)

Same contact form as Task 1, but now every submission is saved into a MySQL
`contacts` table using Spring Data JPA, and a `/contacts` endpoint returns
everything stored so far as JSON.

## Project structure
```
task2-mysql-jpa/
├── pom.xml
├── src/main/java/com/maincrafts/task2/
│   ├── Task2Application.java
│   ├── model/Contact.java              # @Entity mapped to "contacts" table
│   ├── repository/ContactRepository.java
│   └── controller/ContactController.java   # POST /submit, GET /contacts
└── src/main/resources/
    ├── application.properties          # MySQL connection settings
    └── static/index.html               # landing page + contact form
```

## Prerequisites
- MySQL Server running locally (via XAMPP, MySQL Workbench, Docker, etc.)
- JDK 17 and Maven

## Setup

1. **Create the database** (or let Spring create it for you — see below):
   ```sql
   CREATE DATABASE task2_db;
   ```
2. **Edit `src/main/resources/application.properties`** and set your real
   MySQL username/password:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password
   ```
   The URL already includes `createDatabaseIfNotExist=true`, so if your MySQL
   user has permission, the database will be created automatically.
3. **Run the app**:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Open **http://localhost:8080**, submit the form, then visit
   **http://localhost:8080/contacts** to see the saved data as JSON.

Hibernate will auto-create the `contacts` table for you on first run
(`spring.jpa.hibernate.ddl-auto=update`).

## What to check off
- [x] Contact form (Name, Email, Message)
- [x] Spring Boot connected to MySQL via Spring Data JPA
- [x] Submitted data stored in a `contacts` table
- [x] `GET /contacts` endpoint returns all stored data as JSON
