package com.maincrafts.task4.controller;

import com.maincrafts.task4.model.Contact;
import com.maincrafts.task4.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    private ContactRepository repo;

    // Public - anyone can submit the contact form.
    @PostMapping("/submit")
    public Contact saveContact(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String message) {
        Contact contact = new Contact(name, email, message);
        return repo.save(contact);
    }

    // Protected - only logged-in users with role ADMIN can view this
    // (enforced in SecurityConfig, not here).
    @GetMapping("/contacts")
    public List<Contact> getAllContacts() {
        return repo.findAll();
    }
}
