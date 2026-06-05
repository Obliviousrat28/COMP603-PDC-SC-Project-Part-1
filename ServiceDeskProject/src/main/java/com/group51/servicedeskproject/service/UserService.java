/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.service;

import com.group51.servicedeskproject.model.User;
import com.group51.servicedeskproject.repository.UserRepository;

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
            // Profile exists -> Verify password
            if (existingUser.getPassword().equals(password)) {
                return existingUser; // Successful authentication
            } else {
                return null; // Wrong password
            }
        } else {
            // Profile does NOT exist -> Create a new one!
            String assignedRole;
            
            if (userRepository.isDatabaseEmpty()) {
                assignedRole = "ADMIN";   // First person ever gets Admin
            } else {
                assignedRole = "USER";    // Everyone else defaults to User
            }

            User newUser = new User(username, password, assignedRole);
            userRepository.saveUser(newUser);
            return newUser;
        }
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
