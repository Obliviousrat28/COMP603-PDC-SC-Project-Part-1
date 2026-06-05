package com.group51.servicedeskproject.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.group51.servicedeskproject.repository.DatabaseManager;
import com.group51.servicedeskproject.repository.TicketRepository;
import com.group51.servicedeskproject.repository.FileTicketRepository;
import com.group51.servicedeskproject.repository.UserRepository;
import com.group51.servicedeskproject.repository.SqliteUserRepository;
import com.group51.servicedeskproject.service.TicketService;
import com.group51.servicedeskproject.service.UserService;
import com.group51.servicedeskproject.ui.LoginScreen;


/**
 *
 * @author tomwi
 */


public class ServiceDesk {
    public static void main(String[] args) {
        // 0. Initialize SQLite tables
        DatabaseManager.initializeDatabase();

        // 1. Ticket Backend
        TicketRepository repository = new FileTicketRepository();
        TicketService ticketService = new TicketService(repository);

        // 2. User Backend
        UserRepository userRepository = new SqliteUserRepository();
        UserService userService = new UserService(userRepository);

        // 3. Launch portal
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginScreen loginScreen = new LoginScreen(ticketService, userService);

                // 1. Set a solid, clean starting dimension so it never boots at 0px
                loginScreen.setSize(450, 500); 

                // 2. Center it on the user's monitor screen
                loginScreen.setLocationRelativeTo(null);

                // 3. Make it visible
                loginScreen.setVisible(true);
            }
        });
    }
}
