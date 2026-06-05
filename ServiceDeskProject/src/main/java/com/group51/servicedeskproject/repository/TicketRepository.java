/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.repository;

import com.group51.servicedeskproject.model.Ticket;
import java.util.List;

/**
 *
 * @author kyvas
 */
// DAO layer for Ticket
public interface TicketRepository {
    void save(Ticket ticket);

    void update(Ticket ticket);

    Ticket findById(int id);

    List<Ticket> findAll();
}
