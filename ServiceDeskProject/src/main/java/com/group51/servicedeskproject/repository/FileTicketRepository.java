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

    private List<Ticket> cache = new ArrayList<>();
    
    public FileTicketRepository() {
        this.cache = loadFromFile(); // 👈 Make sure this method is actually being called here!
    }
    
    @Override
    public void save(Ticket ticket) {
        cache.add(ticket);
        saveToFile();
    }

    @Override
    public void update(Ticket ticket) {
        Ticket existing = findById(ticket.getId());

        if (existing != null) {
            cache.remove(existing);
            cache.add(ticket);
            saveToFile();
        }
    }
    
    @Override
    public Ticket findById(int id) {
        return cache.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>(cache);
    }
    
    private List<Ticket> loadFromFile() {
        List<Ticket> tickets = new ArrayList<>();

        File file = new File(FILE_PATH);
        if (!file.exists()) return tickets;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 6) {
                    Ticket t = new Ticket(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            Priority.valueOf(parts[4]),
                            parts[3]
                    );

                    t.updateStatus(Status.valueOf(parts[5]));
                    tickets.add(t);
                }
            }

        } catch (Exception e) {
            System.err.println("Error loading file: " + e.getMessage());
        }

        return tickets;
    }
    
    private void saveToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {

            for (Ticket t : cache) {
                out.println(
                        t.getId() + "|" +
                        t.getTitle() + "|" +
                        t.getDescription() + "|" +
                        t.getDepartment() + "|" +
                        t.getPriority() + "|" +
                        t.getStatus()
                );
            }

        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }
}
