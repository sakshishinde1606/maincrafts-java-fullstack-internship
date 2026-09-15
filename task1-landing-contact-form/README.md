# Task 1 — Landing Page + Contact Form

A landing page with a contact form that posts to a Spring Boot backend.
The backend just prints the submitted data to the console (no database yet).

## Project structure
```
task1-landing-contact-form/
├── pom.xml
├── src/main/java/com/maincrafts/task1/
│   ├── Task1Application.java        # Spring Boot entry point
│   └── controller/ContactController.java
└── src/main/resources/
    ├── application.properties
    └── static/index.html            # landing page + contact form
```

## How to run

1. Open this folder (`task1-landing-contact-form`) in VS Code (with the
   **Extension Pack for Java** and **Spring Boot Extension Pack** installed),
   or in IntelliJ.
2. Run it with Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
   (If you don't have the wrapper, just run `mvn spring-boot:run` — you need
   Maven and JDK 17 installed.)
3. Open **http://localhost:8080** in your browser.
4. Fill in the contact form and submit. Watch your terminal — the submitted
   Name / Email / Message will print there, and the browser will show a
   success message.

## What to check off
- [x] Landing page (HTML + CSS)
- [x] Contact form: Name, Email, Message + Submit button
- [x] Spring Boot project set up
- [x] Controller (`POST /contact`) handles the submission
- [x] Data printed to console
