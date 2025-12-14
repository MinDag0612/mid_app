package com.example.core.note;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreNoteStorage implements NoteStorage {
    private final FirebaseFirestore firestore;

    public FirestoreNoteStorage() {
        this(FirebaseFirestore.getInstance());
    }

    FirestoreNoteStorage(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public void add(Note note) {
        firestore.collection("notes").add(note);
    }
}
