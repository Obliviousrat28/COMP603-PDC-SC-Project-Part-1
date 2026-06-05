/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
THIS FILE IS COMPLETETLY REDUNDANT
*/

package com.group51.servicedeskproject.ui;

import com.group51.servicedeskproject.model.Priority;
import com.group51.servicedeskproject.model.Status;
import com.group51.servicedeskproject.model.Ticket;
import com.group51.servicedeskproject.service.TicketService;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        System.out.println("4. Update Priority");
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
                updatePriority();
                break;
            case 0:
                System.out.println("Saving and exiting...");
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    // --- Actions ---

    private void createTicket() {

        int id;

        while (true) {
            System.out.print("Enter ID: ");
            id = Integer.parseInt(scanner.nextLine());

            boolean exists = false;

            for (Ticket t : ticketService.getAllTickets()) {
                if (t.getId() == id) {
                    exists = true;
                    break;
                }
            }

            if (!exists) break;

            System.out.println("ID already exists. Try again.");
        }

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        Priority priority = Priority.LOW;

        ticketService.createTicket(id, title, description, priority, department);

        System.out.println("Ticket created!");
    }

    private void viewTickets() {
        boolean stayingInView = true;

        while (stayingInView) {

            List<Ticket> allTickets = ticketService.getAllTickets();

            System.out.println("\n--- View Tickets ---");
            System.out.println("1. View All Tickets");
            System.out.println("2. View In-Progress and Open Tickets");
            System.out.println("3. View Resolved Tickets");
            System.out.println("0. Back to Main Menu");
            System.out.print("Selection: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    displayList("ALL TICKETS", allTickets);
                    break;

                case 2:
                    List<Ticket> inProgress = allTickets.stream()
                            .filter(t -> t.getStatus() == Status.IN_PROGRESS
                                      || t.getStatus() == Status.OPEN)
                            .collect(Collectors.toList());

                    displayList("IN-PROGRESS & OPEN", inProgress);
                    break;

                case 3:
                    List<Ticket> resolved = allTickets.stream()
                            .filter(t -> t.getStatus() == Status.RESOLVED)
                            .collect(Collectors.toList());

                    displayList("RESOLVED", resolved);
                    break;

                case 0:
                    stayingInView = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
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

        try {
            ticketService.updateStatus(id, status);
            System.out.println("Status updated!");
        } catch (Exception e) {
            System.out.println("Ticket not found.");
        }
    }

    private void updatePriority() {
        System.out.print("Enter Ticket ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("1. LOW");
        System.out.println("2. MEDIUM");
        System.out.println("3. HIGH");
        System.out.println("4. CRITICAL");

        System.out.print("Select priority: ");
        int choice = Integer.parseInt(scanner.nextLine());

        Priority priority;

        switch (choice) {
            case 1:
                priority = Priority.LOW;
                break;
            case 2:
                priority = Priority.MEDIUM;
                break;
            case 3:
                priority = Priority.HIGH;
                break;
            case 4:
                priority = Priority.CRITICAL;
                break;
            default:
                System.out.println("Invalid priority.");
                return;
        }

        try {
            ticketService.updatePriority(id, priority);
            System.out.println("Priority updated!");
        } catch (Exception e) {
            System.out.println("Ticket not found.");
        }
    }

    private void displayList(String title, List<Ticket> list) {
        System.out.println("\n=== " + title + " ===");
        if (list.isEmpty()) {
            System.out.println("No tickets found in this category.");
        } else {
            list.forEach(t -> 
                System.out.println(
                    t.getId() + " | " 
                    + t.getTitle() 
                    + " | [" + t.getStatus() + "]"
                    + " | Priority: " + t.getPriority()
                )
            );
        }
        System.out.println("==============================\n");
    }
}

