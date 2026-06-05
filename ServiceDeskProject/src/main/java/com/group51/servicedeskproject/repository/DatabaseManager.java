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
    // 1. Derby Embedded URL setup. This creates a folder named "ServiceDeskDB" inside your project root
    private static final String URL = "jdbc:derby:ServiceDeskDB_Ebd;create=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        // 2. Updated SQL syntax to be strictly compliant with Apache Derby standards
        String createUsersTable = "CREATE TABLE users ("
                + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, "
                + "username VARCHAR(50) UNIQUE NOT NULL, "
                + "password_hash VARCHAR(255) NOT NULL, "
                + "role VARCHAR(20) DEFAULT 'USER' NOT NULL"
                + ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Check if table already exists so Derby doesn't throw an error trying to recreate it
            if (!tableExists(conn, "USERS")) {
                stmt.execute(createUsersTable);
                System.out.println("Derby Embedded Database initialized successfully.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    // Helper method to keep Derby happy on startup
    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        try (java.sql.ResultSet rs = conn.getMetaData().getTables(null, null, tableName.toUpperCase(), null)) {
            return rs.next();
        }
    }
}