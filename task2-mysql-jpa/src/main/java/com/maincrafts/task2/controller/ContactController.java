package com.maincrafts.task2.controller;

import com.maincrafts.task2.model.Contact;
import com.maincrafts.task2.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private ContactRepository repo;

    // Saves a submitted contact form into the "contacts" table.
    @PostMapping("/submit")
    public Contact saveContact(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String message) {
        Contact contact = new Contact(name, email, message);
        return repo.save(contact);
    }

    // Returns every stored contact as JSON.
    @GetMapping("/contacts")
    public List<Contact> getAllContacts() {
        return repo.findAll();
    }
}
