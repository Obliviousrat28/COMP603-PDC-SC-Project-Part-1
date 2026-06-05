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
    // This repository saves tickets in memory and clears when the app is closed
    // ArrayList to store tickets
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public void saveAll(List<Ticket> tickets) {
        this.tickets = new ArrayList<>(tickets); // copy for safety
    }
    
    @Override
    public List<Ticket> load() {
        return new ArrayList<>(tickets); // return copy
    }
    
    @Override
    public List<Ticket> getAllTickets() {
        // Simply return the memory list this specific class uses (line 8)
        return this.tickets; 
    }
}
