/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.model;

/**
 *
 * @author kyvas
 */
public class Ticket {
    private int id;
    private String title;
    private String description;
    private Priority priority;
    private String department;
    private Status status;

    public Ticket(int id, String title, String description, Priority priority, String department) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.department = department;
        this.status = Status.OPEN;
    }

    // getters and setters
    
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartment() {
        return department;
    }
    
    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}

    
