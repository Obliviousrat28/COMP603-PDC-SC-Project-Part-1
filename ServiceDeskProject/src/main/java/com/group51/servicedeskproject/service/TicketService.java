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
        this.tickets = repository.load();
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
        repository.saveAll(tickets);
    }

    public void closeTicket(int id) {
        updateStatus(id, Status.RESOLVED);
    }
}

