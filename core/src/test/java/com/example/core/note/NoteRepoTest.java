package com.example.core.note;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class NoteRepoTest {
    @Test
    public void addNote_delegatesToStorage() {
        NoteStorage storage = mock(NoteStorage.class);
        NoteRepo noteRepo = new NoteRepo(storage);
        Note payload = new Note("title", "14/12/2025", "content");

        noteRepo.addNote(payload);

        verify(storage).add(payload);
    }
}
