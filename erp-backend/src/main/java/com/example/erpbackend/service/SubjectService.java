package com.example.erpbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.erpbackend.entity.Subject;
import com.example.erpbackend.repository.SubjectRepository;

@Service
public class SubjectService {
	 @Autowired
	    private SubjectRepository subjectRepository;

	    public List<Subject> getAllSubjects() {
	        return subjectRepository.findAll();
	    }

	    public Subject getSubjectById(Long id) {
	        return subjectRepository.findById(id).orElse(null);
	    }
}
