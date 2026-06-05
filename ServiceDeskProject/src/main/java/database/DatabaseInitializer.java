/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author kyvas
 */
public class DatabaseInitializer {
    public static void init() {

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (!tableExists(conn, "TICKETS")) {
                createTickets(conn);
            }

            if (!tableExists(conn, "USERS")) {
                createUsers(conn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void createTickets(Connection conn) throws SQLException {
        String sql =
            "CREATE TABLE Tickets (" +
            "id INT PRIMARY KEY, " +
            "title VARCHAR(255), " +
            "description VARCHAR(1000), " +
            "priority VARCHAR(20), " +
            "department VARCHAR(100), " +
            "status VARCHAR(20)" +
            ")";

        conn.createStatement().executeUpdate(sql);
    }

    private static void createUsers(Connection conn) throws SQLException {
        String sql =
            "CREATE TABLE Users (" +
            "id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
            "username VARCHAR(50), " +
            "password VARCHAR(255), " +
            "role VARCHAR(20)" +
            ")";

        conn.createStatement().executeUpdate(sql);
    }

    private static boolean tableExists(Connection conn, String table) throws SQLException {
        return conn.getMetaData()
                .getTables(null, null, table.toUpperCase(), null)
                .next();
    }
}
