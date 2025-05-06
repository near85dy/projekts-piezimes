package org.example.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Database
{
    private final String FILE_PATH;
    private List<String[]> cachedData;

    public Database(String path)
    {
        if (path.startsWith("file:/")) {
            path = path.substring(6);
        }
        this.FILE_PATH = path;
        this.cachedData = new ArrayList<>();
        
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            loadDatabase();
        } catch (IOException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<String[]> loadDatabase() throws IOException {
        System.out.println("Loading database from: " + FILE_PATH);
        cachedData.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Database file does not exist: " + FILE_PATH);
            return new ArrayList<>(cachedData);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                cachedData.add(line.split(","));
            }
        }
        return new ArrayList<>(cachedData);
    }

    public void saveDatabase() throws IOException {
        System.out.println("Saving database to: " + FILE_PATH); 
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String[] row : cachedData) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
            writer.flush();
        }
    }

    public void displayAll() throws IOException {
        List<String[]> rows = loadDatabase();
        for (String[] row : rows) {
            System.out.println(Arrays.toString(row));
        }
    }

    public void insertRow(String[] newRow) throws IOException {
        cachedData.add(newRow);
        saveDatabase();
    }

    public void updateRow(String id, String[] updatedRow) throws IOException {
        for (int i = 0; i < cachedData.size(); i++) {
            if (cachedData.get(i)[0].equals(id)) {
                cachedData.set(i, updatedRow);
                break;
            }
        }
        saveDatabase();
    }

    public void deleteRow(String id) throws IOException {
        cachedData.removeIf(row -> row[0].equals(id));
        saveDatabase();
    }

    public void shutdown() {
        try {
            saveDatabase();
        } catch (IOException e) {
            System.err.println("Failed to save database during shutdown: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
