/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.repository;

import com.group51.servicedeskproject.model.User;
import java.util.List;

public interface UserRepository {
    void saveUser(User user);
    User findByUsername(String username);
    boolean isDatabaseEmpty(); // Crucial for your first-login bootstrap goal!
    List<User> getAllUsers();   // So the Admin can see profiles to change roles
    void updateUserRole(String username, String newRole);
}
