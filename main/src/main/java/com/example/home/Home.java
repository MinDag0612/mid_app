package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.note.Note;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    RecyclerView rvNotes;
    List<Note> noteList;
    NoteAdapter adapter;
    String email, userId, fullname;
    TextView txtName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra("email");
            userId = intent.getStringExtra("userId");
            fullname = intent.getStringExtra("fullname");
        }

        init();
        setTxtName();
    }

    public void init(){
        txtName = findViewById(R.id.txtName);
        initNotes();
    }

    public void initNotes(){
        rvNotes = findViewById(R.id.rvNotes);
        noteList = new ArrayList<>();
        Log.d("Home", "onCreate called, noteList size = " + noteList.size());

        noteList = new ArrayList<>();
        noteList.add(new Note("Ghi chú 1", "12/12/2025", "Nội dung ghi chú 1..."));
        noteList.add(new Note("Học Android", "13/12/2025", "Hôm nay học RecyclerView..."));
        noteList.add(new Note("Công việc", "14/12/2025", "Danh sách cần làm..."));

        adapter = new NoteAdapter(this, noteList);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setAdapter(adapter);
        Log.d("Home", "onCreate called, noteList size = " + noteList.size());
    }

    public void setTxtName(){
        txtName.setText(fullname);
    }
}
