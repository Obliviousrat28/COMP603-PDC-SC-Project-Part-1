/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.service;

import com.group51.servicedeskproject.model.Priority;
import com.group51.servicedeskproject.model.Status;
import com.group51.servicedeskproject.model.Ticket;
import com.group51.servicedeskproject.repository.TicketRepository;
import java.util.List;

/**
 *
 * @author kyvas
 */
public class TicketService {

    private TicketRepository repository;
    private List<Ticket> tickets;
    
    public TicketService(TicketRepository repository) {
        this.repository = repository;
        this.tickets = repository.getAllTickets();
    }

    public Ticket createTicket(int id, String title, String description,
        Priority priority, String department) {

        Ticket ticket = new Ticket(id, title, description, priority, department);
        tickets.add(ticket);
        return ticket;
    }
    
    public void updateStatus(int id, Status status) {
        Ticket ticket = getTicketById(id);

        if (ticket == null) {                               // null safety
            System.out.println("Ticket not found: " + id);
            return;
        }

        ticket.updateStatus(status);
    }
    
    public void updatePriority(int id, Priority priority) {
        Ticket ticket = getTicketById(id);

        if (ticket == null) {
            System.out.println("Ticket not found: " + id);
            return;
        }

        ticket.setPriority(priority);
    }

    public Ticket getTicketById(int id) {
        return tickets.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Ticket> getAllTickets() {
        return tickets;
    }
    
    public void saveAll() {
        if (this.repository != null) {
            // Pass the live running array list to the disk writer file structure
            this.repository.saveAll(this.tickets);
        }
    }

    public void closeTicket(int id) {
        updateStatus(id, Status.RESOLVED);
    }
    
    public boolean isIdExists(int id) {
        return getTicketById(id) != null; // Iterated through all tickets and found no match.
    }
    
    public int getNextAvailableId() {
        int maxId = 0;
        // Loop through current tickets to find the highest ID number used so far
        for (Ticket t : this.getAllTickets()) {
            if (t.getId() > maxId) {
                maxId = t.getId();
            }
        }
        return maxId + 1; // The next available ID is just the highest ID + 1
    }
}

