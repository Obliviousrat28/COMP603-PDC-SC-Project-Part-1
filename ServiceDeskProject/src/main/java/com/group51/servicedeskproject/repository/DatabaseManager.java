/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.group51.servicedeskproject.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    // This creates a file named "servicedesk.db" in the root directory of your project
    private static final String URL = "jdbc:sqlite:servicedesk.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Call this method once when the app starts up to build your tables automatically
    public static void initializeDatabase() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT UNIQUE NOT NULL, "
                + "password_hash TEXT NOT NULL, "
                + "role TEXT NOT NULL DEFAULT 'ROLE_USER'"
                + ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Execute table creation
            stmt.execute(createUsersTable);
            System.out.println("SQLite database initialized successfully.");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}
