/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.service;

import com.group51.servicedeskproject.model.Role;
import com.group51.servicedeskproject.model.User;
import com.group51.servicedeskproject.repository.UserRepository;
import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Handles authentication and automatic registration if the profile doesn't exist
     */
    public User loginOrCreateProfile(String username, String password) {
        User existingUser = userRepository.findByUsername(username);
    
        if (existingUser != null) {
            // SECURITY FIX: Verify password before allowing login
            if (existingUser.getPassword().equals(password)) {
                System.out.println("[UserService] Logging in existing user: " + username);
                return existingUser;
            } else {
                System.out.println("[UserService] Authentication FAILED for: " + username);
                return null; // Return null to block unauthorized access
            }
        }

        // Logic for new account registration remains the same...
        boolean isFirstAccount = userRepository.isDatabaseEmpty();
        Role assignedRole = isFirstAccount ? Role.ADMIN : Role.USER;

        if (isFirstAccount) {
            System.out.println("[UserService] First user rule triggered! Assigning ADMIN to: " + username);
            assignedRole = Role.ADMIN;
        } else {
            System.out.println("[UserService] Standard user rule triggered. Assigning USER to: " + username);
            assignedRole = Role.USER;
        }

        // 3. Create, save, and return the new user account context
        User newUser = new User(username, password, assignedRole);
        userRepository.saveUser(newUser);

        return newUser;
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 1. ADD THIS: Fetches all registered users for our GUI display list
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    // 2. ADD THIS: Safely changes a user's access role in the database
    public void updateUserRole(String username, Role newRole) {
        userRepository.updateUserRole(username, newRole);
    }
}
