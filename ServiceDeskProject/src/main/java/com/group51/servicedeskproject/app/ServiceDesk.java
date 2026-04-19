package com.group51.servicedeskproject.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.group51.servicedeskproject.repository.InMemoryTicketRepository;
import com.group51.servicedeskproject.repository.TicketRepository;
import com.group51.servicedeskproject.service.TicketService;
import com.group51.servicedeskproject.ui.ConsoleUI;

/**
 *
 * @author tomwi
 */
public class ServiceDesk {
    public static void main(String[] args) {

        TicketRepository repository = new InMemoryTicketRepository();
        TicketService ticketService = new TicketService(repository);

        ConsoleUI ui = new ConsoleUI(ticketService);
        ui.start();
    }
}
