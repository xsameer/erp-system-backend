package com.example.erpbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.erpbackend.entity.SubjectRegistration;
import com.example.erpbackend.repository.SubjectRegistrationRepository;

import java.util.List;

@Service
public class SubjectRegistrationService {

    @Autowired
    private SubjectRegistrationRepository subjectRegistrationRepository;

    public SubjectRegistration registerForSubject(SubjectRegistration registration) {
        registration.setStatus("pending"); // Set default status to "pending"
        return subjectRegistrationRepository.save(registration);
    }

    public SubjectRegistration approveRegistration(Long registrationId) {
        SubjectRegistration registration = subjectRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("No registration found with ID: " + registrationId));
        registration.setStatus("approved"); // Change status to "approved"
        return subjectRegistrationRepository.save(registration);
    }

    public List<SubjectRegistration> getPendingRegistrations() {
        return subjectRegistrationRepository.findByStatus("pending");
    }

    // New method to get approved registrations
    public List<SubjectRegistration> getApprovedRegistrations() {
        return subjectRegistrationRepository.findByStatus("approved");
    }
}
