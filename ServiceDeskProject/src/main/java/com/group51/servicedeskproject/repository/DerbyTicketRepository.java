/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.repository;

import com.group51.servicedeskproject.model.Priority;
import com.group51.servicedeskproject.model.Status;
import com.group51.servicedeskproject.model.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kyvas
 */
public class DerbyTicketRepository implements TicketRepository{

    private final Connection connection;

    public DerbyTicketRepository(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void save(Ticket ticket) {
        String sql = "INSERT INTO Tickets (id, title, description, priority, department, status) "
               + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, ticket.getId());
            stmt.setString(2, ticket.getTitle());
            stmt.setString(3, ticket.getDescription());
            stmt.setString(4, ticket.getPriority().name());
            stmt.setString(5, ticket.getDepartment());
            stmt.setString(6, ticket.getStatus().name());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving ticket: " + e.getMessage());
        }
    }

    @Override
    public void update(Ticket ticket) {
        String sql = "UPDATE Tickets SET title=?, description=?, priority=?, department=?, status=? "
               + "WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, ticket.getTitle());
            stmt.setString(2, ticket.getDescription());
            stmt.setString(3, ticket.getPriority().name());
            stmt.setString(4, ticket.getDepartment());
            stmt.setString(5, ticket.getStatus().name());
            stmt.setInt(6, ticket.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating ticket: " + e.getMessage());
        }
    }

    @Override
    public Ticket findById(int id) {
        String sql = "SELECT * FROM Tickets WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error finding ticket: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT * FROM Tickets";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tickets.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error loading tickets: " + e.getMessage());
        }

        return tickets;
    }
    
    private Ticket mapRow(ResultSet rs) throws SQLException {

        Ticket ticket = new Ticket(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                Priority.valueOf(rs.getString("priority")),
                rs.getString("department")
        );

        ticket.updateStatus(Status.valueOf(rs.getString("status")));

        return ticket;
    }
    
}
