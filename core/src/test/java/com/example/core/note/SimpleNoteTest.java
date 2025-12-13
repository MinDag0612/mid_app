package com.example.core.note;

import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleNoteTest {

    @Test
    public void testNoteConstructor() {
        Note note = new Note("Title", "Date", "Content");
        assertEquals("Title", note.title);
        assertEquals("Date", note.date);
        assertEquals("Content", note.content);
    }

    @Test
    public void testNoteFields() {
        Note note = new Note("A", "B", "C");
        note.title = "New";
        assertEquals("New", note.title);
    }
}
