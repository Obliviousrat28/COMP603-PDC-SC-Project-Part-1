/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author kyvas
 */
public class DatabaseTest {
    public static void checkTicketsTable() {
        String sql = "SELECT * FROM Tickets";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Tickets table EXISTS and is accessible.");

        } catch (Exception e) {
            System.out.println("Tickets table does NOT exist or is not accessible.");
            e.printStackTrace();
        }
    }
}
