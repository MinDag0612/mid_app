package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.core.NoteRepo;

public class NoteDetail extends AppCompatActivity {
    ImageButton btnBack;
    String title, content, noteId;
    EditText etTitle, etContent;
    NoteRepo noteRepo = new NoteRepo();
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable saveRunnable;
    static final long SAVE_DELAY = 600; // ms

    TextWatcher watcher;
    ImageButton shareBtn, deleteBtn;



    private void scheduleSave() {
        if (noteId == null) {
            Log.d("AutoSave", "noteId is NULL → return");
            return;
        }

        if (saveRunnable != null) {
            handler.removeCallbacks(saveRunnable);
        }

        saveRunnable = () -> {
            String newTitle = etTitle.getText().toString().trim();
            String newContent = etContent.getText().toString().trim();

            noteRepo.updateNote(newTitle, newContent, noteId);
            Log.d("AutoSave", "Saved " + noteId + " " + newTitle + " " + newContent);
        };

        handler.postDelayed(saveRunnable, SAVE_DELAY);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_detail);

        init();
        setBtnBack();
        setEtContent();
        setEtTitle();
        setupAutoSave();
        setDeleteBtn(deleteBtn);
    }

    public void init(){
        btnBack = findViewById(R.id.btnBack);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        deleteBtn = findViewById(R.id.deleteBtn);
        shareBtn = findViewById(R.id.shareBtn);


        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        noteId = intent.getStringExtra("noteId");

    }

    public void setBtnBack(){
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void setEtTitle(){
        etTitle.setText(title);
    }

    public void setEtContent() {
        etContent.setText(content);
    }

    private void setupAutoSave() {

        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                scheduleSave();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etTitle.addTextChangedListener(watcher);
        etContent.addTextChangedListener(watcher);
    }

    public void setDeleteBtn(ImageButton deleteBtn) {
        deleteBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa note này không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        // Thực hiện xóa
                        noteRepo.deleteNote(noteId);
                        finish(); // đóng activity sau khi xóa
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> {
                        dialog.dismiss(); // hủy xóa
                    })
                    .show();
        });
    }
}