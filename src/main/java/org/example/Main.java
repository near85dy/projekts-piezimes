package org.example;

import org.example.services.NoteService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        NoteService noteService = new NoteService();
        try{
            noteService.initialize();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(noteService.getAllNotes());

    }
}