package org.example.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database
{
    private static String FILE_PATH = "";

    public Database(String path)
    {
        this.FILE_PATH = path;
    }

    public List<String[]> loadDatabase() throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rows.add(line.split(","));
            }
        }
        return rows;
    }

    // Save memory back into CSV
    public void saveDatabase(List<String[]> rows) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] row : rows) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }

    // Display all rows
    public void displayAll() throws IOException {
        List<String[]> rows = loadDatabase();
        for (String[] row : rows) {
            System.out.println(Arrays.toString(row));
        }
    }

    // Insert a new row
    public void insertRow(String[] newRow) throws IOException {
        List<String[]> rows = loadDatabase();
        rows.add(newRow);
        saveDatabase(rows);
    }

    // Update a row by ID (assumes first column is ID)
    public void updateRow(String id, String[] updatedRow) throws IOException {
        List<String[]> rows = loadDatabase();
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i)[0].equals(id)) {
                rows.set(i, updatedRow);
                break;
            }
        }
        saveDatabase(rows);
    }

    // Delete a row by ID (assumes first column is ID)
    public void deleteRow(String id) throws IOException {
        List<String[]> rows = loadDatabase();
        rows.removeIf(row -> row[0].equals(id));
        saveDatabase(rows);
    }

}
