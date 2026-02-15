package com.rohit.easymoneytracker.service;

import com.rohit.easymoneytracker.entity.Role;
import com.rohit.easymoneytracker.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
    
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Role not found"));
    }
}
