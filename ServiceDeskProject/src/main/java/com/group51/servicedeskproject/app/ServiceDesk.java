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
import com.group51.servicedeskproject.ui.LoginScreen;


/**
 *
 * @author tomwi
 */


public class ServiceDesk {
    public static void main(String[] args) {
        // 1. Initialize your running Ticket backend database repository layer
        com.group51.servicedeskproject.repository.TicketRepository repository = 
            new com.group51.servicedeskproject.repository.FileTicketRepository();
        com.group51.servicedeskproject.service.TicketService ticketService = 
            new com.group51.servicedeskproject.service.TicketService(repository);

        // 2. Initialize your running User backend profile layer
        com.group51.servicedeskproject.repository.UserRepository userRepository = 
            new com.group51.servicedeskproject.repository.SqliteUserRepository(); 
        com.group51.servicedeskproject.service.UserService userService = 
            new com.group51.servicedeskproject.service.UserService(userRepository);

        // 3. Launch the login portal frame with BOTH dependencies passed in
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Fixed: Added 'userService' as the second parameter here!
                LoginScreen loginScreen = new LoginScreen(ticketService, userService);
                loginScreen.pack();
                loginScreen.setLocationRelativeTo(null);
                loginScreen.setVisible(true);
            }
        });
    }
}
