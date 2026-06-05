/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.repository;

import database.DatabaseConnection;
import java.sql.SQLException;

/**
 *
 * @author kyvas
 */
public class RepositoryFactory {
    public static TicketRepository createTicketRepository() throws SQLException {
        return new DerbyTicketRepository(
            DatabaseConnection.getInstance().getConnection()
        );
    }

    public static UserRepository createUserRepository() throws SQLException {
        return new DerbyUserRepository(
            DatabaseConnection.getInstance().getConnection()
        );
    }
}
