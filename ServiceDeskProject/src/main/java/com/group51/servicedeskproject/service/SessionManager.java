/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group51.servicedeskproject.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SessionManager {
    private static final String SESSION_FILE = "user_session.properties";

    /**
     * Saves the logged-in user's username so they skip login next time.
     */
    public static void saveSession(String username) {
        Properties props = new Properties();
        props.setProperty("username", username);
        
        try (FileOutputStream out = new FileOutputStream(SESSION_FILE)) {
            props.store(out, "User Session Cache");
        } catch (IOException e) {
            System.err.println("Could not save login session: " + e.getMessage());
        }
    }

    /**
     * Reads the saved username if a session exists.
     */
    public static String getSavedSession() {
        File file = new File(SESSION_FILE);
        if (!file.exists()) return null;

        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(file)) {
            props.load(in);
            return props.getProperty("username");
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Deletes the session file when the user explicitly logs out.
     */
    public static void clearSession() {
        File file = new File(SESSION_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}
