package com.group51.servicedeskproject.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.group51.servicedeskproject.repository.DatabaseManager;
import com.group51.servicedeskproject.repository.TicketRepository;
import com.group51.servicedeskproject.repository.FileTicketRepository;
import com.group51.servicedeskproject.repository.SqliteUserRepository;
import com.group51.servicedeskproject.repository.UserRepository;
import com.group51.servicedeskproject.service.SessionManager;
import com.group51.servicedeskproject.service.TicketService;
import com.group51.servicedeskproject.service.UserService;
import com.group51.servicedeskproject.ui.GUI;
import com.group51.servicedeskproject.ui.LoginScreen;


/**
 *
 * @author tomwi
 */


public class ServiceDesk {
    public static void main(String[] args) {
        // 1. Initialize your database connection normally
        com.group51.servicedeskproject.repository.DatabaseManager.initializeDatabase();

        // 2. Instantiate your services
        SqliteUserRepository userRepo = new SqliteUserRepository(); 
        UserService userService = new UserService(userRepo);
        
        FileTicketRepository ticketRepo = new FileTicketRepository();
        TicketService ticketService = new TicketService(ticketRepo);

        java.awt.EventQueue.invokeLater(() -> {
            // 3. Check if a user session was already left active
            String savedUsername = SessionManager.getSavedSession();

            if (savedUsername != null) {
                com.group51.servicedeskproject.model.User user = userRepo.findByUsername(savedUsername);
                if (user != null) {
                    System.out.println("Auto-logging in: " + savedUsername);

                    // 1. Instantiate a temporary generic JFrame to serve as a valid parent anchor
                    javax.swing.JFrame dummyParent = new javax.swing.JFrame();
                    dummyParent.setSize(800, 600);
                    dummyParent.setLocationRelativeTo(null);

                    // 2. Pass the dummyParent instead of null so GUI.java line 25 doesn't crash!
                    com.group51.servicedeskproject.ui.GUI mainMenu = new com.group51.servicedeskproject.ui.GUI(dummyParent, ticketService, user);

                    mainMenu.setSize(800, 600);
                    mainMenu.setLocationRelativeTo(null);
                    mainMenu.setVisible(true);
                    return;
                }
            }

            // If no session exists, load up the default Login screen workflow
            // Note: ticketService goes FIRST to match your exact constructor signature!
            LoginScreen login = new LoginScreen(ticketService, userService);
            login.setVisible(true);
        });
    }
}
