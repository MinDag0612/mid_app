package com.example.core.note;

public class NoteRepo {
    private final NoteStorage storage;

    public NoteRepo() {
        this(new FirestoreNoteStorage());
    }

    public NoteRepo(NoteStorage storage) {
        this.storage = storage;
    }

    public void addNote(Note note) {
        storage.add(note);
    }

//    public ArrayList<Note> getNotes(String userId) {
//        ArrayList<Note> notes = new ArrayList<>();
//        db.collection("notes")
//                .whereEqualTo("userId", userId)
//                .get()
//    }
}
