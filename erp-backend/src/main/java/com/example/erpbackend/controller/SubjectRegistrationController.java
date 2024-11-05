package com.example.erpbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.erpbackend.entity.Subject;
import com.example.erpbackend.entity.SubjectRegistration;
import com.example.erpbackend.service.SubjectRegistrationService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class SubjectRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(SubjectRegistrationController.class);

    @Autowired
    private SubjectRegistrationService subjectRegistrationService;

    @PostMapping("/registrations")
    public ResponseEntity<SubjectRegistration> registerForSubject(@RequestBody SubjectRegistration registration) {
        logger.info("Registering for subject: {}", registration);
        SubjectRegistration savedRegistration = subjectRegistrationService.registerForSubject(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegistration);
    }

    @GetMapping("/registrations/pending")
    public ResponseEntity<List<SubjectRegistration>> getPendingRegistrations() {
        logger.info("Fetching pending registrations");
        List<SubjectRegistration> pendingRegistrations = subjectRegistrationService.getPendingRegistrations();
        return ResponseEntity.ok(pendingRegistrations);
    }

    @GetMapping("/registrations/approved")
    public ResponseEntity<List<SubjectRegistration>> getApprovedRegistrations() {
        logger.info("Fetching approved registrations");
        List<SubjectRegistration> approvedRegistrations = subjectRegistrationService.getApprovedRegistrations();
        return ResponseEntity.ok(approvedRegistrations);
    }

    @PutMapping("/registrations/approve/{id}")
    public ResponseEntity<SubjectRegistration> approveRegistration(@PathVariable Long id) {
        logger.info("Approving registration with ID: {}", id);
        SubjectRegistration approvedRegistration = subjectRegistrationService.approveRegistration(id);
        return ResponseEntity.ok(approvedRegistration);
    }

    @PostMapping("/subjects/{subjectId}/apply")
    public ResponseEntity<SubjectRegistration> applyForSubject(@PathVariable Long subjectId, @RequestBody SubjectRegistration registration) {
        logger.info("Applying for subject ID: {} with registration: {}", subjectId, registration);

        // Set the subject based on the ID passed in the URL
        Subject subject = new Subject();
        subject.setId(subjectId);
        registration.setSubject(subject);

        // Check if student information is provided in the request
        if (registration.getStudent() == null || registration.getStudent().getId() == null) {
            logger.error("Student information is required.");
            return ResponseEntity.badRequest().body(null);
        }

        // Set the default status to "pending"
        registration.setStatus("pending");

        // Save and return the registration
        SubjectRegistration savedRegistration = subjectRegistrationService.registerForSubject(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegistration);
    }

    // Exception handler for IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Error occurred: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
