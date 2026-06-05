/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.repository;

import com.group51.servicedeskproject.model.Role;
import com.group51.servicedeskproject.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kyvas
 */
public class DerbyUserRepository implements UserRepository {

    private final Connection connection;

    public DerbyUserRepository(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().name()); // Converts Enum to text for SQL storage

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // FIXED: Read the string from DB and convert it to your safe Role Enum
                Role dbRole = Role.valueOf(rs.getString("role").toUpperCase().trim());
                
                return new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    dbRole
                );
            }

        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean isDatabaseEmpty() {
        String query = "SELECT COUNT(*) FROM Users"; // Make sure this matches your exact table name
        try (PreparedStatement stmt = this.connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int rowCount = rs.getInt(1);
                System.out.println("[Repository Check] Current user count in DB: " + rowCount);
                return rowCount == 0;
            }
        } catch (SQLException e) {
            System.err.println("Database count check failed: " + e.getMessage());
            e.printStackTrace(); // This prints the full stack trace to the console so you can see exactly what failed
            
            // ALTERNATIVE FALLBACK: If the query fails because the table is brand new/doesn't exist yet, 
            // that technically means the database has 0 users. Let's return true here so the first setup can proceed.
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM Users";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // FIXED: Read the string from DB and convert it to your safe Role Enum per loop pass
                Role dbRole = Role.valueOf(rs.getString("role").toUpperCase().trim());
                
                users.add(new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    dbRole
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void updateUserRole(String username, Role newRole) { // FIXED: Parameter changed from String to Role
        String sql = "UPDATE Users SET role = ? WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, newRole.name()); // Saves the Enum name text into the database table row
            stmt.setString(2, username);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
}