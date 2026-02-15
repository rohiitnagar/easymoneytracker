package com.rohit.easymoneytracker.controller;

import com.rohit.easymoneytracker.entity.User;
import com.rohit.easymoneytracker.service.RoleService;
import com.rohit.easymoneytracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @Autowired
    private UserService userService;
    @Autowired private RoleService roleService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/users")
    public String usersList(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/users";
    }
    
    @GetMapping("/users/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/user-form";
    }
    
    @PostMapping("/users")
    public String createUser(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           @RequestParam Long roleId,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findAllRoles());
            return "admin/user-form";
        }
        
        userService.createUserWithRole(user, roleId);
        redirectAttributes.addFlashAttribute("success", "User created successfully!");
        return "redirect:/admin/users";
    }
    
    @PostMapping("/users/{id}/toggle")
    public String toggleUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.toggleUserStatus(id);
        redirectAttributes.addFlashAttribute("success", "User status updated!");
        return "redirect:/admin/users";
    }
}
