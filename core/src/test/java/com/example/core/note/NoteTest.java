package com.example.core.note;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

public class NoteTest {
    private Note note;

    @Before
    public void setup() {
        note = new Note("Test Title", "15/12/2025", "Test Content");
    }

    @Test
    public void testNoteCreation() {
        assertThat(note.title).isEqualTo("Test Title");
        assertThat(note.date).isEqualTo("15/12/2025");
        assertThat(note.content).isEqualTo("Test Content");
    }

    @Test
    public void testNoteWithEmptyFields() {
        Note emptyNote = new Note("", "", "");
        assertThat(emptyNote.title).isEmpty();
        assertThat(emptyNote.date).isEmpty();
        assertThat(emptyNote.content).isEmpty();
    }

    @Test
    public void testNoteFieldsAreMutable() {
        note.title = "Updated Title";
        note.content = "Updated Content";
        
        assertThat(note.title).isEqualTo("Updated Title");
        assertThat(note.content).isEqualTo("Updated Content");
    }
}
