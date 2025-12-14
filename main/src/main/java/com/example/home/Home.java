package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.appcompat.widget.SearchView;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.NoteRepo;
import com.google.firebase.Timestamp;
import com.example.core.note.Note;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    RecyclerView rvNotes;
    List<Note> noteList;
    NoteAdapter adapter;
    String email, userId, fullname;
    TextView txtName;
    NoteRepo noteRepo = new NoteRepo();
    SearchView searchBar;
    ImageView addBtn;
    Handler handler = new Handler(Looper.getMainLooper());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra("email");
            userId = intent.getStringExtra("userId");
            fullname = intent.getStringExtra("fullname");
        }

        init();
        setTxtName();
        setSearchBar();
        setAddBtn();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (userId != null) {
            reloadNotes();
        }
    }

    private void reloadNotes() {
        noteRepo.getNoteById(userId, new NoteRepo.OnNotesLoaded() {
            @Override
            public void onSuccess(ArrayList<Note> notes) {
                adapter.setData(notes);
                Log.d("Home", "Reload notes: " + notes.size());
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("Home", "Reload failed", e);
            }
        });
    }



    public void init(){
        txtName = findViewById(R.id.txtName);
        searchBar = findViewById(R.id.searchBar);
        addBtn = findViewById(R.id.addBtn);
        initNotes();
    }

    public void initNotes(){
        rvNotes = findViewById(R.id.rvNotes);
        noteList = new ArrayList<>();
        adapter = new NoteAdapter(Home.this, noteList, note -> {
            Intent intent = new Intent(Home.this, NoteDetail.class);
            intent.putExtra("title", note.title);
            intent.putExtra("content", note.content);
            intent.putExtra("date", note.date.toDate().getTime());
            intent.putExtra("noteId", note.id);
            startActivity(intent);
        });

        if (userId == null) {
            Log.e("Home", "userId is NULL → redirecting");
            finish();
            return;
        }

        noteRepo.getNoteById(userId, new NoteRepo.OnNotesLoaded() {
            @Override
            public void onSuccess(ArrayList<Note> notes) {
                adapter.setData(notes);

                rvNotes.setLayoutManager(new LinearLayoutManager(Home.this));
                rvNotes.setAdapter(adapter);
                Log.d("Home", "onSuccess: " + notes.size());

            }

            @Override
            public void onFailure(Exception e) {
                Log.d("Home", "onFailure: " + e.getMessage());
            }
        });
    }

    public void setTxtName(){
        txtName.setText(fullname);
    }

    public void setSearchBar(){
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Khi bấm nút search trên bàn phím
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 🔥 Khi text thay đổi (gõ / xoá)
                Log.d("SearchView", "Text = " + newText);

                // filter ở đây
                adapter.filter(newText);
                return true;
            }
        });
    }

    public void setAddBtn(){
        addBtn.setOnClickListener(v -> {
            Note newNote = new Note();
            newNote.title = "Untitled";
            newNote.content = "Content here";
            newNote.date = Timestamp.now();

            noteRepo.addNoteWithUserId(newNote, userId, docRef -> {
                // docRef.getId() là id mới
                newNote.id = docRef.getId();

                // reload notes
                reloadNotes();

                // highlight note mới
                adapter.setHighlightedById(newNote.id);

                // tự tắt highlight sau 2s
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    adapter.setHighlightedById(null);
                }, 2000);
            });
        });
    }

}
