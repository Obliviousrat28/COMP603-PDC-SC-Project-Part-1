/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servicedeskproject;

import java.util.Scanner;

/**
 *
 * @author tomwi
 */
public class ServiceDesk {
    public static void main(String[] args) {
        //place holder text for the title
        System.out.println("Welcome to the software");
        //next outputs are to example what each input does
        System.out.println("type 1 to make a request");
        System.out.println("type 2 to view requests");
        System.out.println("type 3 to update a request status");
        System.out.println("type 4 to update a request priority");
        System.out.println("type 5 to resolve a request");
        System.out.println("type 0 to exit");
        
        //initiate scanner here to allow for user input
        Scanner scanner = new Scanner(System.in);
        
        //put input into a variable to allow switch to work
        int num = scanner.nextInt();
        
        switch(num){
            case 1:
                System.out.println("Opening ticket creation");
                //code to go to a different java file for creation
                break;
        }
    }
}
