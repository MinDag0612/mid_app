package com.example.core.note;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoteRepo {
    private final NoteStorage storage;

    public NoteRepo() {
        this(new FirestoreNoteStorage());
    }

    public NoteRepo(NoteStorage storage) {
        this.storage = storage;
    }

    // Trong NoteRepo
    public void addNoteWithUserId(Note note, String userId, OnNoteAdded callback) {
        Map<String, Object> data = new HashMap<>();
        data.put("title", note.title);
        data.put("content", note.content);
        data.put("date", note.date);
        data.put("user_id", userId);

        db.collection("Note").add(data)
                .addOnSuccessListener(docRef -> {
                    note.id = docRef.getId();
                    Log.d("NoteRepo", "Added note with id: " + note.id);
                    if (callback != null) callback.onAdded(docRef);
                })
                .addOnFailureListener(e -> {
                    Log.e("NoteRepo", "Failed to add note", e);
                });
    }

    public interface OnNoteAdded {
        void onAdded(DocumentReference docRef);
    }



    public void getNoteById(String userId, OnNotesLoaded callback) {
        ArrayList<Note> notes = new ArrayList<>();

        db.collection("Note")
                .whereEqualTo("user_id", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Note note = doc.toObject(Note.class);

                            note.id = doc.getId();
                            notes.add(note);
                        }
                        callback.onSuccess(notes);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }


    public interface OnNotesLoaded {
        void onSuccess(ArrayList<Note> notes);

        void onFailure(Exception e);
    }

    public void updateNote(String title, String content, String noteId) {
        db.collection("Note")
                .document(noteId)
                .update(
                        "title", title,
                        "content", content
                );
    }

    public void deleteNote(String noteId) {
        db.collection("Note").document(noteId).delete();
    }


}

