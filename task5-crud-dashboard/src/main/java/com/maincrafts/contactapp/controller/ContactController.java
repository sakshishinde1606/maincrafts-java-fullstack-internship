package com.maincrafts.contactapp.controller;

import com.maincrafts.contactapp.dto.ContactRequest;
import com.maincrafts.contactapp.exception.ResourceNotFoundException;
import com.maincrafts.contactapp.model.Contact;
import com.maincrafts.contactapp.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactController {

    @Autowired
    private ContactRepository repo;

    // ---------------------------------------------------------------
    // PUBLIC - the landing page contact form still posts here, same as
    // Task 1-4. Anyone can submit an inquiry without logging in.
    // ---------------------------------------------------------------
    @PostMapping("/submit")
    public Contact submitPublicContact(@RequestParam String name,
                                        @RequestParam String email,
                                        @RequestParam String message) {
        Contact contact = new Contact(name, email, message);
        return repo.save(contact);
    }

    // ---------------------------------------------------------------
    // ADMIN-ONLY CRUD - protected by SecurityConfig ("/contacts/**").
    // ---------------------------------------------------------------

    // GET /contacts?page=0&size=10&sort=name,asc
    @GetMapping("/contacts")
    public Page<Contact> list(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "createdAt,desc") String sort) {

        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        Sort.Direction direction = (sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc"))
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return repo.findAll(pageable);
    }

    // Admin manually adding a new lead/contact.
    @PostMapping("/contacts")
    public Contact create(@Valid @RequestBody ContactRequest request) {
        Contact contact = new Contact(request.name(), request.email(), request.message());
        if (request.status() != null && !request.status().isBlank()) {
            contact.setStatus(request.status());
        }
        return repo.save(contact);
    }

    // Edit an existing contact (fix a typo, update message/status, etc.)
    @PutMapping("/contacts/{id}")
    public Contact update(@PathVariable Long id, @Valid @RequestBody ContactRequest request) {
        Contact contact = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id " + id));

        contact.setName(request.name());
        contact.setEmail(request.email());
        contact.setMessage(request.message());
        if (request.status() != null && !request.status().isBlank()) {
            contact.setStatus(request.status());
        }
        return repo.save(contact);
    }

    // Delete a contact.
    @DeleteMapping("/contacts/{id}")
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Contact not found with id " + id);
        }
        repo.deleteById(id);
    }
}
