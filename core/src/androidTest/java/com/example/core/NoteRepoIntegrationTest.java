package com.example.core;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.core.note.Note;
import com.example.core.note.NoteRepo;

@RunWith(AndroidJUnit4.class)
public class NoteRepoIntegrationTest {
    private NoteRepo noteRepo;
    private Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
        noteRepo = new NoteRepo();
    }

    @Test
    public void testNoteRepoWithFirebase() {
        Note note = new Note("Integration Test", "14/12/2025", "Test content");
        noteRepo.addNote(note);
    }
}
