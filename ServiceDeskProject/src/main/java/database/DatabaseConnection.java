/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kyvas
 */
public class DatabaseConnection {
    
    private static DatabaseConnection instance;
    private Connection connection;
    
    private static final String URL = "jdbc:derby:ServiceDeskDB_Ebd;create=true";
    
    private DatabaseConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            this.connection = DriverManager.getConnection(URL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    // Singleton Pattern to ensure only one instance of DatabaseConnection is created
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
