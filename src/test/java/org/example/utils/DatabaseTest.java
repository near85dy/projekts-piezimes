package org.example.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @TempDir
    Path tempDir;

    private Database database;
    private String dbPath;

    @BeforeEach
    public void setUp() {
        dbPath = tempDir.resolve("test_db.csv").toString();
        database = new Database(dbPath);
    }

    @Test
    public void testLoadDatabaseEmpty() throws IOException {
        List<String[]> result = database.loadDatabase();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testInsertAndLoadDatabase() throws IOException {
        String[] row1 = {"1", "test1", "value1"};
        String[] row2 = {"2", "test2", "value2"};
        
        database.insertRow(row1);
        database.insertRow(row2);
        
        List<String[]> result = database.loadDatabase();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        
        assertArrayEquals(row1, result.get(0));
        
        assertArrayEquals(row2, result.get(1));
    }

    @Test
    public void testUpdateRow() throws IOException {
        String[] row1 = {"1", "test1", "value1"};
        String[] row2 = {"2", "test2", "value2"};
        
        database.insertRow(row1);
        database.insertRow(row2);
        
        String[] updatedRow = {"1", "test1_updated", "value1_updated"};
        database.updateRow("1", updatedRow);
        
        List<String[]> result = database.loadDatabase();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        
        assertArrayEquals(updatedRow, result.get(0));
        
        assertArrayEquals(row2, result.get(1));
    }

    @Test
    public void testDeleteRow() throws IOException {
        String[] row1 = {"1", "test1", "value1"};
        String[] row2 = {"2", "test2", "value2"};
        
        database.insertRow(row1);
        database.insertRow(row2);
        
        database.deleteRow("1");
        
        List<String[]> result = database.loadDatabase();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        
        assertArrayEquals(row2, result.get(0));
    }

    @Test
    public void testShutdown() throws IOException {
        String[] row = {"1", "test", "value"};
        database.insertRow(row);
        
        database.shutdown();
        
        File dbFile = new File(dbPath);
        assertTrue(dbFile.exists());
    }
} 