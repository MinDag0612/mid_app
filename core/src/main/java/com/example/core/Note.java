package com.example.core;

import com.google.firebase.Timestamp;

public class Note {
    public String id;       // 🔥 noteId
    public String title;
    public Timestamp date;
    public String content;
    public String user_id;


    public Note() {} // bắt buộc cho Firestore

    public Note(String id, String title, Timestamp date, String content) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
    }
}
