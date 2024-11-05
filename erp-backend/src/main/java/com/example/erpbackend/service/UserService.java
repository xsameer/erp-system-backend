package com.example.erpbackend.service;

import com.example.erpbackend.entity.User;
import com.example.erpbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    public void saveUser(User user) {
//        userRepository.save(user);
//    }
    public void saveUser(User user) {
        // Assign the role as "student" if no role is specified
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("student");
        }
        userRepository.save(user);
    }

//    public boolean authenticate(String email, String password) {
//        User user = userRepository.findByEmail(email);
//        return user != null && user.getPassword().equals(password);
//    }
    
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Return user if authentication is successful
        }
        return null; // Return null if authentication fails
    }
    
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
