package org.example;

import org.example.models.NoteModel;
import org.example.models.UserModel;
import org.example.services.NoteService;
import org.example.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
                System.out.println("3. View Statistics");
                System.out.println("4. Exit");
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
                        displayStatistics();
                        break;
                    case 4:
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
            System.out.println("3. Update Profile");
            System.out.println("4. Logout");
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
                    updateProfile();
                    break;
                case 4:
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
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
            }
        }
    }

    private static void updateProfile() {
        UserModel currentUser = userService.getCurrentUser();
        System.out.println("\n=== Update Profile ===");
        System.out.println("Current Profile Information:");
        System.out.println("Name: " + currentUser.name);
        System.out.println("Surname: " + currentUser.surname);
        System.out.println("Age: " + currentUser.age);
        
        System.out.print("\nEnter new name (press Enter to keep current): ");
        String newName = scanner.nextLine();
        if (newName.isEmpty()) {
            newName = currentUser.name;
        }
        
        System.out.print("Enter new surname (press Enter to keep current): ");
        String newSurname = scanner.nextLine();
        if (newSurname.isEmpty()) {
            newSurname = currentUser.surname;
        }
        
        System.out.print("Enter new password (press Enter to keep current): ");
        String newPassword = scanner.nextLine();
        if (newPassword.isEmpty()) {
            newPassword = currentUser.password;
        }
        
        System.out.print("Enter new age (press Enter to keep current): ");
        String ageInput = scanner.nextLine();
        int newAge = currentUser.age;
        if (!ageInput.isEmpty()) {
            try {
                newAge = Integer.parseInt(ageInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid age format. Keeping current age.");
            }
        }
        
        try {
            UserModel updatedUser = userService.updateUserProfile(newName, newSurname, newPassword, newAge);
            System.out.println("\nProfile updated successfully!");
            System.out.println("Updated Profile Information:");
            System.out.println("Name: " + updatedUser.name);
            System.out.println("Surname: " + updatedUser.surname);
            System.out.println("Age: " + updatedUser.age);
        } catch (IOException e) {
            System.out.println("Error updating profile: " + e.getMessage());
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void viewNotes() {
        while (true) {
            clearConsole();
            System.out.println("\n=== View Notes ===");
            System.out.println("1. View All Notes");
            System.out.println("2. Sort by Title (A-Z)");
            System.out.println("3. Sort by Title (Z-A)");
            System.out.println("4. Sort by Last Update (Newest First)");
            System.out.println("5. Sort by Last Update (Oldest First)");
            System.out.println("6. Update Note");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            clearConsole();

            List<NoteModel> notes;
            switch (choice) {
                case 1:
                    notes = noteService.getCurrentUserNotes();
                    break;
                case 2:
                    notes = noteService.getNotesSortedByTitle(true);
                    break;
                case 3:
                    notes = noteService.getNotesSortedByTitle(false);
                    break;
                case 4:
                    notes = noteService.getNotesSortedByLastUpdate(false);
                    break;
                case 5:
                    notes = noteService.getNotesSortedByLastUpdate(true);
                    break;
                case 6:
                    updateNote();
                    continue;
                case 7:
                    return;
                default:
                    System.out.println("Invalid option!");
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    continue;
            }

            if (notes.isEmpty()) {
                System.out.println("You don't have any notes yet!");
            } else {
                System.out.println("\nYour Notes:");
                for (NoteModel note : notes) {
                    System.out.println(note);
                }
            }
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void updateNote() {
        List<NoteModel> notes = noteService.getCurrentUserNotes();
        if (notes.isEmpty()) {
            System.out.println("You don't have any notes to update!");
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.println("\n=== Update Note ===");
        System.out.println("Your Notes:");
        for (NoteModel note : notes) {
            System.out.println("ID: " + note.id + " - " + note.title);
        }

        System.out.print("\nEnter the ID of the note you want to update: ");
        int noteId;
        try {
            noteId = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid note ID!");
            scanner.nextLine();
            return;
        }

        Optional<NoteModel> noteToUpdate = notes.stream()
                .filter(note -> note.id == noteId)
                .findFirst();

        if (noteToUpdate.isEmpty()) {
            System.out.println("Note not found!");
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }

        NoteModel note = noteToUpdate.get();
        System.out.println("\nCurrent Note Information:");
        System.out.println(note);

        System.out.print("\nEnter new title (press Enter to keep current): ");
        String newTitle = scanner.nextLine();
        if (newTitle.isEmpty()) {
            newTitle = note.title;
        }

        System.out.println("Enter new content (press Enter to keep current):");
        String newContent = scanner.nextLine();
        if (newContent.isEmpty()) {
            newContent = note.content;
        }

        try {
            NoteModel updatedNote = noteService.updateNote(noteId, newTitle, newContent);
            System.out.println("\nNote updated successfully!");
            System.out.println(updatedNote);
        } catch (IOException e) {
            System.out.println("Error updating note: " + e.getMessage());
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

    private static void displayStatistics() {
        clearConsole();
        System.out.println("\n=== Application Statistics ===");
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ User Statistics:                                           ║");
        System.out.printf("║ Total Users: %-45d ║\n", userService.getTotalUsers());
        System.out.printf("║ Average User Age: %-40.1f ║\n", userService.getAverageUserAge());
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║ Note Statistics:                                           ║");
        System.out.printf("║ Total Notes: %-46d ║\n", noteService.getTotalNotes());
        System.out.printf("║ Average Note Length: %-38.1f ║\n", noteService.getAverageNoteLength());
        System.out.printf("║ Most Recent Note Date: %-35s ║\n", noteService.getMostRecentNoteDate());
        if (userService.isLoggedIn()) {
            System.out.printf("║ Your Notes: %-48d ║\n", noteService.getTotalNotesForCurrentUser());
        }
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}