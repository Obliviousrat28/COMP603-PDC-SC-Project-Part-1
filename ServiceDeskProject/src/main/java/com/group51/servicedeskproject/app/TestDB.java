/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.app;

import database.DatabaseConnection;
import java.sql.Connection;

/**
 *
 * @author kyvas
 */
public class TestDB {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
