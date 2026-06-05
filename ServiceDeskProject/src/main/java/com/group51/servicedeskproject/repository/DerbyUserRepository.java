/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.repository;

import com.group51.servicedeskproject.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
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
            stmt.setString(3, user.getRole());

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
                return new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean isDatabaseEmpty() {
        String sql = "SELECT COUNT(*) FROM Users";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM Users";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void updateUserRole(String username, String newRole) {
        String sql = "UPDATE Users SET role = ? WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, newRole);
            stmt.setString(2, username);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
