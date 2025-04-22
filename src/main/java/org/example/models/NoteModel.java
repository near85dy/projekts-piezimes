package org.example.models;

public class NoteModel {
    public int id;
    public int user_id;
    public String title;
    public String content;
    public String created_at;
    public String updated_at;

    @Override
    public String toString() {
        return String.format("Note{title='%s', content='%s', created_at='%s'}",
                title, content, created_at);
    }
}
