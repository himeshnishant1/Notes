package com.example.notes;

import java.sql.Date;
import java.sql.Time;

public class DataModel {
    private int id;
    private String noteTitle;
    private String noteDateTime;
    private String noteMessage;
    private int color;

    public DataModel(int id, String noteTitle, String noteDateTime, String noteMessage, int color) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteDateTime = noteDateTime;
        this.noteMessage = noteMessage;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDateTime() {
        return noteDateTime;
    }

    public void setNoteDateTime(String noteDateTime) {
        this.noteDateTime = noteDateTime;
    }

    public String getNoteMessage() {
        return noteMessage;
    }

    public void setNoteMessage(String noteMessage) {
        this.noteMessage = noteMessage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
