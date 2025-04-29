package org.example.services;

import org.example.interfaces.IService;
import org.example.models.NoteModel;
import org.example.utils.Database;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteService implements IService
{
    private Database database;
    private List<NoteModel> notes;
    private UserService userService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public NoteService(UserService userService) {
        this.notes = new ArrayList<>();
        String dbPath = System.getProperty("user.dir") + "/src/main/resources/notes.csv";
        this.database = new Database(dbPath);
        this.userService = userService;
    }

    protected Database getDatabase() {
        return database;
    }

    public void initialize() throws IOException {
        List<String[]> dbNotes = database.loadDatabase();

        for(int i = 1; i < dbNotes.size(); i++) {
            String[] note = dbNotes.get(i);
            NoteModel noteModel = new NoteModel();
            noteModel.id = Integer.parseInt(note[0]);
            noteModel.user_id = Integer.parseInt(note[1]);
            noteModel.title = note[2];
            noteModel.content = note[3];
            noteModel.created_at = note[4];
            noteModel.updated_at = note[5];

            notes.add(noteModel);
        }
    }

    public List<NoteModel> getCurrentUserNotes() {
        if (!userService.isLoggedIn()) {
            return new ArrayList<>();
        }
        return notes.stream()
                .filter(note -> note.user_id == userService.getCurrentUser().id)
                .collect(Collectors.toList());
    }

    public NoteModel createNote(String title, String content) throws IOException {
        if (!userService.isLoggedIn()) {
            return null;
        }

        NoteModel newNote = new NoteModel();
        newNote.id = notes.size() + 1;
        newNote.user_id = userService.getCurrentUser().id;
        newNote.title = title;
        newNote.content = content;
        String currentTime = LocalDateTime.now().format(DATE_FORMATTER);
        newNote.created_at = currentTime;
        newNote.updated_at = currentTime;

        notes.add(newNote);

        String[] noteData = {
            String.valueOf(newNote.id),
            String.valueOf(newNote.user_id),
            newNote.title,
            newNote.content,
            newNote.created_at,
            newNote.updated_at
        };
        database.insertRow(noteData);
        
        return newNote;
    }

    public void destroy() {}
}
