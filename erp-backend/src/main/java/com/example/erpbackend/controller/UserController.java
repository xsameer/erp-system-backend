package com.example.erpbackend.controller;

import com.example.erpbackend.entity.User;
import com.example.erpbackend.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        userService.saveUser(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully!");
        response.put("userId", String.valueOf(user.getId())); // Add userId to the response
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        User authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());
        Map<String, String> response = new HashMap<>();
        if (authenticatedUser != null) {
            response.put("userId", String.valueOf(authenticatedUser.getId())); // Add userId to the response
            response.put("name", authenticatedUser.getName());
            response.put("role", authenticatedUser.getRole());
            response.put("message", "Login successful!");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid email or password.");
            return ResponseEntity.status(401).body(response);
        }
    }
}
