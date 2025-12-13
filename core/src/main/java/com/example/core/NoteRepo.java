package com.example.core;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NoteRepo {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void addNote(Note note) {
        db.collection("notes").add(note);
    }

//    public ArrayList<Note> getNotes(String userId) {
//        ArrayList<Note> notes = new ArrayList<>();
//        db.collection("notes")
//                .whereEqualTo("userId", userId)
//                .get()
//    }
}
