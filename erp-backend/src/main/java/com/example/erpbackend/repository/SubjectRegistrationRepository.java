package com.example.erpbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.erpbackend.entity.SubjectRegistration;

import java.util.List;

public interface SubjectRegistrationRepository extends JpaRepository<SubjectRegistration, Long> {
    List<SubjectRegistration> findByStatus(String status); // Custom query method
}
