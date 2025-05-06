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
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════════════════════════╗\n");
        sb.append(String.format("║ %-60s ║\n", "Title: " + title));
        sb.append("╠════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ %-60s ║\n", "Content:"));
        String[] contentLines = content.split("\n");
        for (String line : contentLines) {
            sb.append(String.format("║ %-60s ║\n", line));
        }
        sb.append("╠════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ %-60s ║\n", "Created: " + created_at));
        sb.append(String.format("║ %-60s ║\n", "Last Updated: " + updated_at));
        sb.append("╚════════════════════════════════════════════════════════════╝\n");
        return sb.toString();
    }
}
