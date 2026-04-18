/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.repository;

import com.group51.servicedeskproject.model.Ticket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kyvas
 */
public class InMemoryTicketRepository implements TicketRepository {
    
    // ArrayList to store tickets
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public void save(Ticket ticket) {
        tickets.add(ticket);
    }

    @Override
    public Ticket findById(int id) {
        return tickets.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Ticket> findAll() {
        return tickets;
    }
}
