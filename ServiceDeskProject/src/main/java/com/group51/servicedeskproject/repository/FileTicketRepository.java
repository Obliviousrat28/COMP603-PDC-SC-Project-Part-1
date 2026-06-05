/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.repository;

import com.group51.servicedeskproject.model.Priority;
import com.group51.servicedeskproject.model.Status;
import com.group51.servicedeskproject.model.Ticket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileTicketRepository implements TicketRepository {

    private static final String FILE_PATH = "tickets.txt";

    private List<Ticket> tickets;
    
    public FileTicketRepository() {
        this.tickets = load(); // 👈 Make sure this method is actually being called here!
    }
    
    @Override
    public void saveAll(List<Ticket> tickets) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {

            for (Ticket t : tickets) {
                // Format: id|title|description|department|priority|status
                out.println(t.getId() + "|" +
                            t.getTitle() + "|" +
                            t.getDescription() + "|" +
                            t.getDepartment() + "|" +
                            t.getPriority() + "|" +
                            t.getStatus());
            }

        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    @Override
    public List<Ticket> load() {
        List<Ticket> tickets = new ArrayList<>();

        File file = new File(FILE_PATH);
        if (!file.exists()) return tickets;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String desc = parts[2];
                    String dept = parts[3];
                    Priority priority = Priority.valueOf(parts[4]);
                    Status status = Status.valueOf(parts[5]);

                    Ticket ticket = new Ticket(id, title, desc, priority, dept);
                    ticket.updateStatus(status);

                    tickets.add(ticket);
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading from file: " + e.getMessage());
        }

        return tickets;
    }
    
    @Override
    public List<Ticket> getAllTickets() {
        // Simply return the list we loaded on startup!
        return this.tickets;
    }
}
