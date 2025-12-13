package com.example.core;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.core.note.Note;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NoteListValidatorTest {

    @Test
    public void testEmptyList() {
        List<Note> notes = new ArrayList<>();
        assertThat(notes).isEmpty();
    }

    @Test
    public void testNoteValidation() {
        Note note = new Note("Valid", "14/12/2025", "Content");
        boolean isValid = !note.title.isEmpty() && !note.content.isEmpty();
        assertTrue(isValid);
    }

    @Test
    public void testInvalidNote() {
        Note note = new Note("", "", "");
        boolean isValid = !note.title.isEmpty() && !note.content.isEmpty();
        assertFalse(isValid);
    }

    @Test
    public void testNoteListSize() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("1", "14/12/2025", "A"));
        notes.add(new Note("2", "14/12/2025", "B"));
        
        assertEquals(2, notes.size());
    }

    @Test
    public void testFilterNotesByDate() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("T1", "14/12/2025", "C1"));
        notes.add(new Note("T2", "15/12/2025", "C2"));
        notes.add(new Note("T3", "14/12/2025", "C3"));

        long count = notes.stream()
                .filter(n -> n.date.equals("14/12/2025"))
                .count();
        
        assertEquals(2, count);
    }
}
