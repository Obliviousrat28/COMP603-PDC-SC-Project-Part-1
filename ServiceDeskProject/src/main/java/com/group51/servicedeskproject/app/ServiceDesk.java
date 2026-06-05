package com.group51.servicedeskproject.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.group51.servicedeskproject.repository.DerbyTicketRepository;
import com.group51.servicedeskproject.repository.DerbyUserRepository;
import com.group51.servicedeskproject.repository.RepositoryFactory;
import com.group51.servicedeskproject.repository.TicketRepository;
import com.group51.servicedeskproject.repository.UserRepository;
import com.group51.servicedeskproject.service.SessionManager;
import com.group51.servicedeskproject.service.TicketService;
import com.group51.servicedeskproject.service.UserService;
import com.group51.servicedeskproject.ui.GUI;
import com.group51.servicedeskproject.ui.LoginScreen;
import database.DatabaseConnection;
import database.DatabaseInitializer;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author tomwi
 */


public class ServiceDesk {
    public static void main(String[] args) throws SQLException {
        // 1. Initialize your database connection        
        DatabaseInitializer.init();
        
        // 2. Instantiate your services
        UserRepository userRepository = RepositoryFactory.createUserRepository();
        TicketRepository ticketRepository = RepositoryFactory.createTicketRepository();
        
        UserService userService = new UserService(userRepository);
        TicketService ticketService = new TicketService(ticketRepository);
        
        java.awt.EventQueue.invokeLater(() -> {
            // 3. Check if a user session was already left active
            String savedUsername = SessionManager.getSavedSession();

            if (savedUsername != null) {
                com.group51.servicedeskproject.model.User user = userRepository.findByUsername(savedUsername);
                if (user != null) {
                    System.out.println("Auto-logging in: " + savedUsername);

                    // 1. Instantiate a temporary generic JFrame to serve as a valid parent anchor
                    javax.swing.JFrame dummyParent = new javax.swing.JFrame();
                    dummyParent.setSize(800, 600);

                    // 2. Pass the dummyParent instead of null so GUI.java line 25 doesn't crash!
                    GUI mainMenu = new com.group51.servicedeskproject.ui.GUI(dummyParent, ticketService, user);

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
