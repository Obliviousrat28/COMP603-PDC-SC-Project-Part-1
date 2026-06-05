/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.app;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kyvas
 */
public class TestDB {
    public static void main(String[] args) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        System.out.println("Database connected successfully!");

        DatabaseMetaData meta = conn.getMetaData();
        ResultSet rs = meta.getTables(null, null, "TICKETS", null);

        if (rs.next()) {
            System.out.println("Tickets table exists!");
        } else {
            System.out.println("Tickets table NOT found.");
        }
    }
}
