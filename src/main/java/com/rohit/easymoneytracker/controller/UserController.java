package com.rohit.easymoneytracker.controller;

import com.rohit.easymoneytracker.dto.RegisterRequest;
import com.rohit.easymoneytracker.entity.User;
import com.rohit.easymoneytracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        // ... set other fields
        user.setPassword(passwordEncoder.encode(request.getPassword())); // BCrypt
        return ResponseEntity.ok(userService.createUser(user, "ROLE_USER"));
    }
}
