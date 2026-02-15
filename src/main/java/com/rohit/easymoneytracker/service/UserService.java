package com.rohit.easymoneytracker.service;

import com.rohit.easymoneytracker.entity.Role;
import com.rohit.easymoneytracker.entity.User;
import com.rohit.easymoneytracker.repositories.RoleRepository;
import com.rohit.easymoneytracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user, String roleName) {
        Role role = (Role) roleRepository.findByName(roleName)
            .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void createUserWithRole(User user, Long roleId) {
        Role role = roleService.findById(roleId);
        user.getRoles().clear(); // Single role only
        user.getRoles().add(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
       user.setEnabled(!user.setEnabled());
        userRepository.save(user);
    }
}
