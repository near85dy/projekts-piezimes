package org.example.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NoteModelTest {

    @Test
    public void testNoteModelCreation() {
        NoteModel note = new NoteModel();
        
        note.id = 1;
        note.user_id = 100;
        note.title = "Test Note";
        note.content = "This is a test note content";
        note.created_at = "2023-01-01 12:00:00";
        note.updated_at = "2023-01-01 12:00:00";
        
        assertEquals(1, note.id);
        assertEquals(100, note.user_id);
        assertEquals("Test Note", note.title);
        assertEquals("This is a test note content", note.content);
        assertEquals("2023-01-01 12:00:00", note.created_at);
        assertEquals("2023-01-01 12:00:00", note.updated_at);
    }
    
    @Test
    public void testToString() {
        NoteModel note = new NoteModel();
        note.title = "Test Note";
        note.content = "This is a test note content";
        note.created_at = "2023-01-01 12:00:00";
        
        String expected = "Note{title='Test Note', content='This is a test note content', created_at='2023-01-01 12:00:00'}";
        
        assertEquals(expected, note.toString());
    }
} 