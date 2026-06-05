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

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public Ticket createTicket(int id, String title, String description,
                           Priority priority, String department) {

        if (repository.findById(id) != null) {
            System.out.println("Ticket ID already exists");
            return null;
        }

        Ticket ticket = new Ticket(id, title, description, priority, department);
        repository.save(ticket);
        return ticket;
    }
    
    public void updateStatus(int id, Status status) {

        Ticket ticket = repository.findById(id);

        if (ticket == null) {
            System.out.println("Ticket not found: " + id);
            return;
        }

        ticket.updateStatus(status);
        repository.update(ticket);
    }
    
    public void updatePriority(int id, Priority priority) {

        Ticket ticket = repository.findById(id);

        if (ticket == null) {
            System.out.println("Ticket not found: " + id);
            return;
        }

        ticket.setPriority(priority);
        repository.update(ticket);
    }
    
    public Ticket getTicketById(int id) {
        return repository.findById(id);
    }
    
    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    public boolean isIdExists(int id) {
        return repository.findById(id) != null;
    }

    public int getNextAvailableId() {
        int maxId = 0;

        for (Ticket t : repository.findAll()) {
            if (t.getId() > maxId) {
                maxId = t.getId();
            }
        }

        return maxId + 1;
    }
}

