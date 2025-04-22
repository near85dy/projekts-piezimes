package org.example;

import org.example.models.NoteModel;
import org.example.models.UserModel;
import org.example.services.NoteService;
import org.example.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static UserService userService;
    private static NoteService noteService;
    private static Scanner scanner;

    public static void main(String[] args) {
        try {
            initialize();
            mainMenu();
        } catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
        }
    }

    private static void initialize() throws IOException {
        scanner = new Scanner(System.in);
        userService = new UserService();
        userService.initialize();
        noteService = new NoteService(userService);
        noteService.initialize();
    }

    private static void mainMenu() {
        while (true) {
            if (!userService.isLoggedIn()) {
                clearConsole();
                System.out.println("\n=== Welcome to Notes App ===");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();
                clearConsole();

                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        register();
                        break;
                    case 3:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } else {
                userMenu();
            }
        }
    }

    private static void login() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        UserModel user = userService.login(email, password);
        if (user != null) {
            clearConsole();
            System.out.println("Welcome back, " + user.name + "!");
        } else {
            System.out.println("Invalid email or password!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void register() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        String emailError = userService.validateEmail(email);
        if (emailError != null) {
            System.out.println("Error: " + emailError);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        try {
            UserModel user = userService.register(email, name, surname, password, age);
            clearConsole();
            System.out.println("Registration successful! Please login.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        } catch (IOException e) {
            System.out.println("Error during registration: " + e.getMessage());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void userMenu() {
        while (userService.isLoggedIn()) {
            clearConsole();
            System.out.println("\n=== Notes Menu ===");
            System.out.println("1. View My Notes");
            System.out.println("2. Create New Note");
            System.out.println("3. Logout");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            clearConsole();

            switch (choice) {
                case 1:
                    viewNotes();
                    break;
                case 2:
                    createNote();
                    break;
                case 3:
                    userService.destroy();
                    noteService.destroy();
                    System.out.println("Logged out successfully!");
                    try {
                        Thread.sleep(1500); 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return;
                default:
                    System.out.println("Invalid option!");
                    try {
                        Thread.sleep(1500); // Show error message for 1.5 seconds
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
            }
        }
    }

    private static void viewNotes() {
        List<NoteModel> notes = noteService.getCurrentUserNotes();
        if (notes.isEmpty()) {
            System.out.println("You don't have any notes yet!");
        } else {
            System.out.println("\nYour Notes:");
            for (NoteModel note : notes) {
                System.out.println("----------------------------------------");
                System.out.println(note);
            }
        }
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void createNote() {
        System.out.print("Enter note title: ");
        String title = scanner.nextLine();
        System.out.print("Enter note content: ");
        String content = scanner.nextLine();

        try {
            NoteModel note = noteService.createNote(title, content);
            if (note != null) {
                clearConsole();
                System.out.println("Note created successfully!");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                System.out.println("Failed to create note!");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException e) {
            System.out.println("Error creating note: " + e.getMessage());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
            }
        }
    }
}