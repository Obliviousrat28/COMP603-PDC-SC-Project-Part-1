/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.ui;

import com.group51.servicedeskproject.model.Priority;
import com.group51.servicedeskproject.model.Status;
import com.group51.servicedeskproject.model.Ticket;
import com.group51.servicedeskproject.service.TicketService;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author kyvas
 */
public class ConsoleUI {

    private TicketService ticketService;
    private Scanner scanner;

    public ConsoleUI(TicketService ticketService) {
        this.ticketService = ticketService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;

        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            handleChoice(choice);

        } while (choice != 0);

        System.out.println("Exiting system...");
    }

    private void showMenu() {
        System.out.println("\n=== Service Desk System ===");
        System.out.println("1. Create Ticket");
        System.out.println("2. View Tickets");
        System.out.println("3. Update Status");
        System.out.println("4. Resolve Ticket");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1:
                createTicket();
                break;
            case 2:
                viewTickets();
                break;
            case 3:
                updateStatus();
                break;
            case 4:
                resolveTicket();
                break;
            case 0:
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    // --- Actions ---

    private void createTicket() {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        checkId(id);

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        Priority priority = Priority.LOW; // keep simple for now

        ticketService.createTicket(id, title, description, priority, department);

        System.out.println("Ticket created!");
    }
    
    private void checkId(int ID) {
        List<Ticket> tickets = ticketService.getAllTickets();

        if (tickets.isEmpty()) {
            return;
        }

        for (Ticket t : tickets) {
            if (ID == t.getId()) {
                System.out.println("Warning: Ticket ID " + ID + " already exists!");
                // You might want to throw an error or ask for a new ID here
            }
        }
    }

    private void viewTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();

        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        tickets.forEach(t ->
            System.out.println(
                t.getId() + " | " +
                t.getTitle() + " | " +
                t.getStatus()
            )
        );
    }

    private void updateStatus() {
        System.out.print("Enter Ticket ID: ");
        int id = scanner.nextInt();

        System.out.println("1. OPEN");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. RESOLVED");

        int choice = scanner.nextInt();

        Status status;

        switch (choice) {
            case 1:
                status = Status.OPEN;
                break;
            case 2:
                status = Status.IN_PROGRESS;
                break;
            case 3:
                status = Status.RESOLVED;
                break;
            default:
                status = null;
                break;
        }

        if (status != null) {
            ticketService.updateStatus(id, status);
            System.out.println("Status updated!");
        } else {
            System.out.println("Invalid status");
        }
    }

    private void resolveTicket() {
        System.out.print("Enter Ticket ID: ");
        int id = scanner.nextInt();

        ticketService.updateStatus(id, Status.RESOLVED);

        System.out.println("Ticket resolved!");
    }
}
