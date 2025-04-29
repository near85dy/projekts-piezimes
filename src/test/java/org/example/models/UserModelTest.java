package org.example.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserModelTest {

    @Test
    public void testUserModelCreation() {
        // Create a new UserModel
        UserModel user = new UserModel();
        
        user.id = 1;
        user.email = "test@example.com";
        user.name = "John";
        user.surname = "Doe";
        user.password = "password123";
        user.age = 30;
        
        assertEquals(1, user.id);
        assertEquals("test@example.com", user.email);
        assertEquals("John", user.name);
        assertEquals("Doe", user.surname);
        assertEquals("password123", user.password);
        assertEquals(30, user.age);
    }
} 