package com.group51.servicedeskproject.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.group51.servicedeskproject.repository.FileTicketRepository;
import com.group51.servicedeskproject.repository.TicketRepository;
import com.group51.servicedeskproject.service.TicketService;
import com.group51.servicedeskproject.ui.ConsoleUI;
import com.group51.servicedeskproject.ui.GUI;

/**
 *
 * @author tomwi
 */


public class ServiceDesk {
    public static void main(String[] args) {
        // 1. Keep your working backend logic exactly as it is!
        TicketRepository repository = new FileTicketRepository();
        TicketService ticketService = new TicketService(repository);

        // 2. Replace ConsoleUI with your GUI launch wrapper
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // FIX: Pass the running backend engine right here into the parentheses!
                GUI startScreen = new GUI(ticketService); 

                // Center it and show it
                startScreen.setLocationRelativeTo(null);
                startScreen.setVisible(true);
            }
        });
    }
}
