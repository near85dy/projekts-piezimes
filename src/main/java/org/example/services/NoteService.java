package org.example.services;

import org.example.interfaces.IService;
import org.example.models.NoteModel;
import org.example.utils.Database;

import java.io.IOException;
import java.util.List;

public class NoteService implements IService
{
    private Database database;
    private List<NoteModel> notes;

    public NoteService() {
        this.database = new Database(getClass().getClassLoader().getResource("notes.csv").getFile());
    }

    public void initialize() throws IOException {
        List<String[]> dbNotes = database.loadDatabase();
        for(String[] note : dbNotes)
        {
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

    public String getAllNotes() {
        return notes.toString();
    }

    public void createNote(NoteModel note) {

    }

    public void destroy() {}
}
