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
    private final String FILE_PATH = "tickets.txt";
    private List<Ticket> tickets = new ArrayList<>();

    public FileTicketRepository() {
        loadFromFile();
    }

    @Override
    public void save(Ticket ticket) {
        // Remove if existing to prevent duplicates, then add the updated version
        tickets.removeIf(t -> t.getId() == ticket.getId());
        tickets.add(ticket);
        saveToFile();
    }

    @Override
    public Ticket findById(int id) {
        return tickets.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Ticket> findAll() {
        return tickets;
    }

    private void saveToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Ticket t : tickets) {
                // Formatting: id|title|description|department|priority|status
                out.println(t.getId() + "|" + t.getTitle() + "|" + t.getDescription() + "|" + 
                            t.getDepartment() + "|" + t.getPriority() + "|" + t.getStatus());
            }
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

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

                    Ticket t = new Ticket(id, title, desc, priority, dept);
                    t.updateStatus(status);
                    tickets.add(t);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading from file: " + e.getMessage());
        }
    }
}
